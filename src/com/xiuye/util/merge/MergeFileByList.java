package com.xiuye.util.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.xiuye.util.Pointer;
import com.xiuye.util.log.XLog;

public class MergeFileByList {

	private static final String ROOT_PATH = "C:/download";

	public static void mergeFile(Path path) throws IOException {
		
		Path outputPath = Paths.get(ROOT_PATH + "/" + "output/" + path.getFileName() + ".coff");
		if(!Files.exists(outputPath)) {
			Path parentPath = outputPath.getParent();
			if (Objects.nonNull(parentPath)) // null will be returned if the path has no parent
			{
				Files.createDirectories(parentPath);
			}
//			Files.createDirectories(outputPath);
			Files.createFile(outputPath);
		}
		OutputStream out = Files.newOutputStream(outputPath);
		
		Pointer.of(Files.newBufferedReader(path)).ifPresentV(reader -> {
			String dataLine = null;
			try {
				while ((dataLine = reader.readLine()) != null) {
//					XLog.lg(dataLine.matches("^#.*"),dataLine);
					if (!dataLine.isEmpty() && !dataLine.matches("^$(.*")) {
						String patchFile = dataLine.replace("/", ROOT_PATH);
						Pointer.of(Files.newInputStream(Paths.get(patchFile))).ifPresentV(stream -> {
							byte[] data = new byte[1024 * 4];
							int len = -1;
							try {
								XLog.lg(patchFile, stream.available());
								while ((len = stream.read(data, 0, 1024 * 4)) != -1) {
//											XLog.lg(new String(data,StandardCharsets.UTF_8));
									appendDataToFile(out, data, 0, len);
								}
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								try {
									stream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (Objects.nonNull(reader)) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(Objects.nonNull(out)) {
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	private static void appendDataToFile(OutputStream out, byte[] data, int offset, int len) throws IOException {
		try {
			out.write(data, offset, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final void appendDataToFile(byte[] data, String file) {

	}

	public static void main(String[] args) throws IOException {

		Files.list(Paths.get(ROOT_PATH)).forEach(path -> {
			XLog.lg(Files.isDirectory(path), path);
			if (!Files.isDirectory(path)) {
				XLog.lg("Open", path);
				try {
					mergeFile(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
				XLog.lg("Over", path);
			}
		});

	}

}
