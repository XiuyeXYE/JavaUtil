package com.xiuye.util.security;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.xiuye.sharp.X;
import com.xiuye.util.cls.XType;



/**
 * 第一步，随机选择两个不相等的质数p和q。
 * 第二步，计算p和q的乘积n。
 * 第三步，计算n的欧拉函数φ(n)。
 * 第四步，随机选择一个整数e，条件是1< e < φ(n)，且e与φ(n) 互质。
 * 第五步，计算e对于φ(n)的模反元素d。
 * 
 * @author Administrator
 *
 */
public class RSA {
	
	private BigInteger p,q;
	private BigInteger n;
	private BigInteger Euler_n;
	
	private BigInteger pubKey;
	private BigInteger priKey;
	
	private publicKey pubK;
	private privateKey priK;
	
	{
		
		X.lnS("Primes number:",primes.size());
		
		p = primes.get(rand.nextInt(primes.size()));//随机大素数p
		X.lnS("p:",p);
		q = primes.get(rand.nextInt(primes.size()));//随机大素数q
		X.lnS("q:",q);
		n = p.multiply(q);//n=p*q
		X.lnS("n:",n);
		BigInteger pt = p.subtract(BigInteger.ONE);//p-1
		X.lnS("p-1:",pt);
		BigInteger qt = q.subtract(BigInteger.ONE);//q-1
		X.lnS("q-1:",qt);
		Euler_n = pt.multiply(qt);// (p-1)*(q-1) //欧拉函数
		X.lgS("Euler function value:",Euler_n);

		
//		根据互质数的bai定义，可总结出一些规du律，利用这些规律能迅速判断一组数是否互质。
//		（1）两个不相同的质数一定是互质数。如：7和11、17和31是互质数。
//		（2）两个连续的自然数一定是互质数。如：4和5、13和14是互质数。
//		（3）相邻的两个奇数一定是互质数。如：5和7、75和77是互质数。
//		（4）1和其他所有的自然数一定是互质数。如：1和4、1和13是互质数。
//		（5）两个数中的较大一个是质数，这两个数一定是互质数。如：3和19、16和97是互质数。
//		（6）两个数中的较小一个是质数，而较大数是合数且不是较小数的倍数，这两个数一定是互质数。如：2和15、7和54是互质数。
//		（7）较大数比较小数的2倍多1或少1，这两个数一定是互质数。如：13和27、13和25是互质数。
		
		//选择公钥 中的 e
//		　ed ≡ 1 (mod φ(n))
		// 不要使用 Euler(x) -1 的 质数 ,否则得出 后面x,y结果是 -1 和 1
		//非常的不好!
//		pubKey = Euler_n.subtract(BigInteger.ONE);//according to (2)
		
		//=65537
		pubKey = crtBigInt("65537");
		X.lnS("public key:",pubKey);
		
		//计算模反元素 d
		//e => pubKey
		//d => priKey
		//d => x
//		　ed ≡ 1 (mod φ(n))
//		<=>　ed - 1 = kφ(n)
//		<=>  ex + φ(n)y = 1
		XY xy = new XY();
		extendEuclidGCD(pubKey,Euler_n, xy);
		
//		X.lnS(xy.x,xy.y);
		
		
		priKey = xy.x;
		
		X.lnS("private key:",priKey);
		
		pubK = new publicKey();
		
		priK = new privateKey();
		
		pubK.N = n;
		pubK.pubKey = pubKey;
		
		priK.N = n;
		priK.priKey = priKey;
		
	}
	
	private class publicKey{
		private BigInteger pubKey;
		private BigInteger N;
	}
	private class privateKey{
		private BigInteger priKey;
		private BigInteger N;
	}
	
	//for function output!
	private class XY{
		private BigInteger x = BigInteger.ZERO;
		private BigInteger y = BigInteger.ZERO;
	}
	
//	贝祖定理（一般形式）
//	对于不全为 0 的自然数 a，ba，b，则必然存在整数 x，yx，y （不唯一）满足等式
//	ax+by=gcd(a,b)
	//if a和b 互质 ,then  gcd(a,b) == 1
	//即 ax + by = 1;
//	若整数 a，ba，b 互质，则存在整数解 x，yx，y 满足 ax+by=1
	//扩展欧几里得算法
	private BigInteger extendEuclidGCD(BigInteger a,BigInteger b,XY xy) {
		
		//b == 0
		if(BigInteger.ZERO.equals(b)) {
			xy.x = BigInteger.ONE;//x = 1
			xy.y = BigInteger.ZERO;//y = 0
			return a;
		}
//		X.lnS(a,b);
		BigInteger r = extendEuclidGCD(b, a.mod(b), xy);
		
		//t = x
		BigInteger t = xy.x;
//		x = y
		xy.x = xy.y;
		//y = t - a/b*y;
		xy.y = t.subtract(a.divide(b).multiply(xy.y));
		
		X.lnS(a,b,xy.x,xy.y);
		
		return r;		
	}
	
	
	private static BigInteger maxBigNum = crtBigInt("70000");
	
	private static BigInteger startBigNum = crtBigInt("5000"); 
	
	private static List<BigInteger> primes;
	private static Random rand;
	
	static {

		primes = XType.list();
		
		BigInteger numMax = maxBigNum;

		for(BigInteger i = startBigNum;i.compareTo(numMax)<0;i=i.add(BigInteger.ONE)) {
//			X.lnS(i,isPrime(i));
			if(isPrime(i)) {
				primes.add(i);
			}
		}
		rand = new Random(System.currentTimeMillis());
		
	}
	
	
	private static boolean isPrime(BigInteger i) {
		BigInteger n = i.divide(BigInteger.valueOf(2)).add(BigInteger.ONE);
//		X.lnS(i,n,BigInteger.ZERO.compareTo(n),i.mod(n));
		for(BigInteger j=BigInteger.valueOf(2);j.compareTo(n)<0;j=j.add(BigInteger.ONE)) {
//			X.lnS(i,j);
//			X.lnS(i.mod(j));
			if(BigInteger.ZERO.equals(i.mod(j))) {
				return false;
			}
		}
		return true;
	}
	
	private static BigInteger crtBigInt(String val) {
		return new BigInteger(val);
	}
	
	private static BigInteger crtBigInt(byte[] val) {
		return new BigInteger(val);
	}
	
	public RSA() {
	}
	
	private static BigInteger pow(BigInteger num,BigInteger n) {
		BigInteger sum = BigInteger.ONE;
		for(BigInteger i=BigInteger.ZERO;i.compareTo(n)<0;i=i.add(BigInteger.ONE)) {
			sum = sum.multiply(num);
		}
		return sum;
	}
	
	//encrypt by public key
	public byte[] encryptByPubKey(byte []data) {
		List<Byte> ret = XType.list();
		//N 存储的 byte的大小确定存储单元的长度
		int byteMemNum = pubK.N.toByteArray().length;
		//每一个数据都是 byteMemNum 个 byte 为单元存储的!
		if(Objects.nonNull(data)) {
			for(int i=0;i<data.length;i++) {
//				X.lnS(pubK.pubKey,pubK.N,data[i]);
				BigInteger encodeInt = pow(BigInteger.valueOf(data[i]),pubK.pubKey).mod(pubK.N);
				byte [] encodeBytes = encodeInt.toByteArray();
				//填充余下空间 0
				int restBytesLength = byteMemNum - encodeBytes.length;
				if(restBytesLength>0) {
					for(int j=0;j<restBytesLength;j++) {
						ret.add((byte)0);
					}
				}
				for(byte d : encodeBytes) {
					ret.add(d);
				}
//				X.lnS(crtBigInt(encodeBytes));
				
			}
		}	
		
		return list2Bytes(ret);
	}
	//decrypt by private key
	public byte[] decryptByPriKey(byte []data) {
		List<Byte> ret = XType.list();
		//N 存储的 byte的大小确定存储单元的长度
		int byteMemNum = priK.N.toByteArray().length;
		if(Objects.nonNull(data)) {
			//以存储的单元大小来处理数据
			for(int i=0;i<data.length;i+=byteMemNum) {
				byte [] decodeBytes = new byte[byteMemNum];
				for(int j=0;j<byteMemNum;j++) {
					decodeBytes[j] = data[i+j];
				}
//				X.lnS(crtBigInt(decodeBytes));
				BigInteger decodeInt = pow(crtBigInt(decodeBytes),priK.priKey).mod(priK.N);
//				X.lnS(priK.priKey,priK.N,decodeInt);
				for(byte b:decodeInt.toByteArray()) {
//					X.lnS(b);
					ret.add(b);
				}
			}
		}
		
		return list2Bytes(ret);
	}
	
	public byte[] list2Bytes(List<Byte> data) {
		byte[] ret = new byte[data.size()];
		for(int i=0;i<ret.length;i++) {
			ret[i] = data.get(i);					
		}
		
		return ret;
	}
	
	public static void main(String[] args) {
		
		X.lnS(isPrime(BigInteger.valueOf(10)));
		X.lnS(isPrime(BigInteger.valueOf(11)));
		
//		primes.forEach(d->{
//			X.lnS(d);
//		});
		
//		RSA rsa = new RSA();
//		byte [] data = rsa.encryptByPubKey("123456".getBytes());
//		X.lnS(new String(rsa.decryptByPriKey(data)));
//		65537 574744487 54 
//		-136072447 574744487 1
//		302847963
//		X.lnS(pow(crtBigInt("54"),crtBigInt("65537")).mod(crtBigInt("574744487")));
//		X.lnS(pow(crtBigInt("302847963"),crtBigInt("1121606477")).mod(crtBigInt("574744487")));
	}
	
}
