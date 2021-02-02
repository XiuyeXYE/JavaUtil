package com.xiuye.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.DateTimeException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新值 返回 新对象X
 * 旧值 返回 this
 * V 返回 this
 * R 返回 新对象X
 *
 * @param <T>
 * @author Dell
 */
public final class Pointer<T> {
    private static final Pointer<?> EMPTY = new Pointer<>();
    private static final Gson GSON = new Gson();
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Gson getGson() {
        return GSON;
    }

    public static Gson getPrettyGson() {
        return PRETTY_GSON;
    }

    private final T value;

    private Pointer() {
        this.value = null;
    }

    public static <T> Pointer<T> empty() {
        @SuppressWarnings("unchecked")
        Pointer<T> t = (Pointer<T>) EMPTY;
        return t;
    }


    private Pointer(T value) {
        this.value = value;
    }

    public static <T> Pointer<T> of(T value) {
        return new Pointer<>(value);
    }

    public static <T> Pointer<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public static <U, I> U cast(I in) {
        return (U) in;
    }

    public static <U> List<U> list() {
        return new ArrayList<>();
    }

    public static <U> Pointer<List<U>> list(Class<U> uClass) {
        return of(list());
    }

    public static <U> Pointer<Set<U>> set(Class<U> uClass) {
        return of(set());
    }

    public static <U> Set<U> set() {
        return new HashSet<>();
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<>(16);
    }

    public static void throwEx(String msg) {
        throw new RuntimeException(msg);
    }

    public <U> U cast() {
        return cast(value);
    }

    public T get() {
//        if (value == null) {
//            throw new NoSuchElementException("No value present");
//        }
        return value;
    }

    public static <U> boolean isNull(U u) {
        return Objects.isNull(u);
    }

    public static <U> boolean nonNull(U u) {
        return Objects.nonNull(u);
    }

    public static <U> boolean isEmpty(Collection<U> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static <U> boolean nonEmpty(Collection<U> collection) {
        return nonNull(collection) && !collection.isEmpty();
    }

    public static <U, E extends Collection<U>> Pointer<U> nonEmptyV(E collection, Consumer<E> consumer) {
        if (nonEmpty(collection)) {
            consumer.accept(collection);
        }
        return empty();
    }

    public static <U, E extends Collection<U>, R> Pointer<R> nonEmptyR(E collection, Function<E, R> function) {
        if (nonEmpty(collection)) {
            return ofNullable(function.apply(collection));
        }
        return empty();
    }

    public static <U, E extends Collection<U>> Pointer<E> isEmptyV(E collection, Runnable runnable) {
        if (isEmpty(collection)) {
            runnable.run();
        }
        return ofNullable(collection);
    }

    public static <U, E extends Collection<U>> Pointer<E> isEmptyR(E collection, Supplier<E> supplier) {
        if (isEmpty(collection)) {
            return ofNullable(supplier.get());
        }
        return ofNullable(collection);
    }


    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return isNull(map) || map.isEmpty();
    }

    public static <K, V> boolean nonEmpty(Map<K, V> map) {
        return nonNull(map) && !map.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || str.isEmpty();
    }

    public static boolean nonEmpty(String str) {
        return nonNull(str) && !str.isEmpty();
    }

    public static <A> boolean isEmpty(A[] arr) {
        return isNull(arr) || arr.length == 0;
    }

    public static <A> boolean nonEmpty(A[] arr) {
        return nonNull(arr) && arr.length > 0;
    }

    public static boolean isEmpty(int[] arr) {
        return isNull(arr) || arr.length == 0;
    }

    public static boolean nonEmpty(int[] arr) {
        return nonNull(arr) && arr.length > 0;
    }

    public boolean isNull() {
        return isNull(value);
    }

    public boolean nonNull() {
        return nonNull(value);
    }


    public boolean isPresent() {
        return nonNull();
    }

    public boolean isAbsent() {
        return isNull();
    }

    public boolean isEmpty() {
        if (isNull()) {
            return true;
        }
        try {
            Class<?> clazz = value.getClass();
            Method m = clazz.getDeclaredMethod("isEmpty");
            boolean b = cast(m.invoke(value));
            return b;
        } catch (Exception e) {
            throw new RuntimeException("not have isEmpty function");
        }
    }


    public boolean nonEmpty() {
        if (nonNull()) {
            try {
                Class<?> clazz = value.getClass();
                Method m = clazz.getDeclaredMethod("isEmpty");
                boolean b = cast(m.invoke(value));
                return !b;
            } catch (Exception e) {
                throw new RuntimeException("not have isEmpty function");
            }
        }
        return false;
    }


    public Pointer<T> ifPresentV(Consumer<? super T> action) {
        if (isPresent()) {
            action.accept(value);
        }
        return this;
    }

    public <U> Pointer<U> ifPresentR(Function<? super T, ? extends U> mapper) {
        if (isPresent()) {
            return ofNullable(mapper.apply(value));
        }
        return empty();
    }

    public Pointer<T> ifAbsentV(Runnable runnable) {
        if (isAbsent()) {
            runnable.run();
        }
        return this;
    }

    public Pointer<T> ifAbsentR(Supplier<? extends T> supplier) {
        if (isAbsent()) {
            return ofNullable(supplier.get());
        }
        return this;
    }

    public Pointer<T> ifPresentOrElseV(Consumer<? super T> action, Runnable emptyAction) {
        if (isPresent()) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
        return this;
    }

    public <U> Pointer<U> ifPresentOrElseR(Function<? super T, ? extends U> mapper, Supplier<U> emptyAction) {
        if (isPresent()) {
            return ofNullable(mapper.apply(value));
        } else {
            return ofNullable(emptyAction.get());
        }
    }

    public Pointer<T> ifAbsentOrElseV(Runnable emptyAction, Consumer<? super T> action) {
        if (isAbsent()) {
            emptyAction.run();
        } else {
            action.accept(value);
        }
        return this;
    }

    public <U> Pointer<U> ifAbsentOrElseR(Supplier<U> emptyAction, Function<? super T, ? extends U> mapper) {
        if (isAbsent()) {
            return ofNullable(emptyAction.get());
        } else {
            return ofNullable(mapper.apply(value));
        }
    }

    public Pointer<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    public <U> Pointer<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return Pointer.ofNullable(mapper.apply(value));
        }
    }

    public <U> Pointer<U> flatMap(Function<? super T, ? extends Pointer<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            Pointer<U> r = (Pointer<U>) mapper.apply(value);
            return Objects.requireNonNull(r);
        }
    }

    public Pointer<T> or(Supplier<? extends Pointer<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            Pointer<T> r = (Pointer<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }

    public T orElse(T other) {
        return isPresent() ? value : other;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return isPresent() ? value : supplier.get();
    }

    public T orElseThrow() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public <E extends Throwable> T orElseThrow(Supplier<? extends E> exceptionSupplier) throws E {
        if (isPresent()) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Pointer)) {
            return false;
        }

        Pointer<?> other = (Pointer<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return isPresent()
                ? String.format("Pointer[%s]", value)
                : "Pointer.empty";
    }

    public static PrintStream setOut(PrintStream out) {
        PrintStream oldOut = System.out;
        System.setOut(out);
        return oldOut;
    }

    public static PrintStream setErr(PrintStream err) {
        PrintStream oldErr = System.err;
        System.setErr(err);
        return oldErr;
    }

    public static PrintStream getOut() {
        return System.out;
    }

    public static PrintStream getErr() {
        return System.err;
    }

    public Pointer<T> logItself() {
        log(value);
        return this;
    }

    public Pointer<T> errItself() {
        err(value);
        return this;
    }


    public Pointer<T> printItself() {
        print(value);
        return this;
    }


    public Pointer<T> printlnItself() {
        println(value);
        return this;
    }

    public Pointer<T> lgItself() {
        lg(value);
        return this;
    }

    public Pointer<String> toJson() {
        return ofNullable(GSON.toJson(Objects.requireNonNull(value)));
    }

    public Pointer<String> toPrettyJson() {
        return ofNullable(PRETTY_GSON.toJson(Objects.requireNonNull(value)));
    }

    public <U> Pointer<U> toObject(Class<U> clazz) {
        Objects.requireNonNull(value);
        if (value instanceof Reader) {
            Reader json = cast(value);
            return ofNullable(GSON.fromJson(json, clazz));
        } else if (value instanceof JsonElement) {
            Reader json = cast(value);
            return ofNullable(GSON.fromJson(json, clazz));
        }
        //default string
        String json = cast(value);

        return ofNullable(GSON.fromJson(json, clazz));
    }


    @SafeVarargs
    public static <N> Pointer<N[]> log(N... t) {
        if (t.length == 0) {
            System.out.println();
        } else {
            for (int i = 0; i < t.length - 1; i++) {
                System.out.print(t[i] + " ");
            }
            System.out.println(t[t.length - 1]);
        }
        return ofNullable(t);
    }


    @SafeVarargs
    public static <N> Pointer<N[]> err(N... t) {
        if (t.length == 0) {
            System.err.println();
        } else {
            for (int i = 0; i < t.length - 1; i++) {
                System.err.print(t[i] + " ");
            }
            System.err.println(t[t.length - 1]);
        }
        return ofNullable(t);
    }


    @SafeVarargs
    public static <N> Pointer<N[]> print(N... ts) {
        if (ts.length > 0) {
            for (int i = 0; i < ts.length - 1; i++) {
                System.out.print(ts[i] + " ");
            }
            System.out.print(ts[ts.length - 1]);
        }
        return ofNullable(ts);
    }


    @SafeVarargs
    public static <N> Pointer<N[]> println(N... t) {
        return log(t);
    }

    @SafeVarargs
    public static <N> Pointer<N[]> lg(N... t) {
        return log(t);
    }


    public <U> Pointer<U> thenR(Function<? super T, ? extends U> thenPhase) {
        Objects.requireNonNull(thenPhase);
        return ofNullable(thenPhase.apply(value));
    }

    public Pointer<T> thenV(Consumer<? super T> thenPhase) {
        Objects.requireNonNull(thenPhase);
        thenPhase.accept(value);
        return this;
    }


    // 适合引用类型 ,不适合 基本类型

//    /**
//     * cast T to R In general,T is ancestor class , R is subclass
//     *
//     * @param <R  extends T>
//     * @param <T>
//     * @param e   instantiated object
//     * @return reference of subclass
//     */
//    @SuppressWarnings("unchecked")
//    public static <R extends T, T> R cast(T e) {
//        return (R) e;
//    }

    // boolean byte char short int long float double

    /**
     * for default constructor
     *
     * @param <R>
     * @author xiuye
     */
    public interface DefaultConstructor<R> {
        /**
         * c
         *
         * @return
         */
        R construct();
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T>
     * @author xiuye
     */
    public interface ConstructorWithParam<R, T> {
        /**
         * c
         *
         * @param t
         * @return
         */
        R construct(T t);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @author xiuye
     */
    public interface ConstructorWithTwoParams<R, T1, T2> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @return
         */
        R construct(T1 t1, T2 t2);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @author xiuye
     */
    public interface ConstructorWithThreeParams<R, T1, T2, T3> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @author xiuye
     */
    public interface ConstructorWithFourParams<R, T1, T2, T3, T4> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @author xiuye
     */
    public interface ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @param t5
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @author xiuye
     */
    public interface ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @param t5
         * @param t6
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @author xiuye
     */
    public interface ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @param t5
         * @param t6
         * @param t7
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @author xiuye
     */
    public interface ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @param t5
         * @param t6
         * @param t7
         * @param t8
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param <T9>
     * @author xiuye
     */
    public interface ConstructorWithNineParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @param t5
         * @param t6
         * @param t7
         * @param t8
         * @param t9
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param <T9>
     * @param <T10>
     * @author xiuye
     */
    public interface ConstructorWithTenParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
        /**
         * c
         *
         * @param t1
         * @param t2
         * @param t3
         * @param t4
         * @param t5
         * @param t6
         * @param t7
         * @param t8
         * @param t9
         * @param t10
         * @return
         */
        R construct(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10);
    }

    /**
     * for parameters-ed constructor
     *
     * @param <R>
     * @param <T>
     * @author xiuye
     */
    public interface ConstructorWithParams<R, T> {
        /**
         * c
         *
         * @param t
         * @return
         */
        @SuppressWarnings("unchecked")
        R construct(T... t);
    }

    /**
     * instantiate object by default constructor
     *
     * @param <R>
     * @param c
     * @return
     */
    public static <R> R newInstance(DefaultConstructor<R> c) {
        return c.construct();
    }

    public static <R> Pointer<R> of(DefaultConstructor<R> c) {
        return of(newInstance(c));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T>
     * @param c
     * @param t
     * @return
     */
    public static <R, T> R newInstance(ConstructorWithParam<R, T> c, T t) {
        return c.construct(t);
    }

    public static <R, T> Pointer<R> of(ConstructorWithParam<R, T> c, T t) {
        return of(newInstance(c, t));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param c
     * @param t1
     * @param t2
     * @return
     */
    public static <R, T1, T2> R newInstance(ConstructorWithTwoParams<R, T1, T2> c, T1 t1, T2 t2) {
        return c.construct(t1, t2);
    }

    public static <R, T1, T2> Pointer<R> of(ConstructorWithTwoParams<R, T1, T2> c, T1 t1, T2 t2) {
        return of(newInstance(c, t1, t2));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @return
     */
    public static <R, T1, T2, T3> R newInstance(ConstructorWithThreeParams<R, T1, T2, T3> c, T1 t1, T2 t2, T3 t3) {
        return c.construct(t1, t2, t3);
    }

    public static <R, T1, T2, T3> Pointer<R> of(ConstructorWithThreeParams<R, T1, T2, T3> c, T1 t1, T2 t2, T3 t3) {
        return of(newInstance(c, t1, t2, t3));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @return
     */
    public static <R, T1, T2, T3, T4> R newInstance(ConstructorWithFourParams<R, T1, T2, T3, T4> c, T1 t1, T2 t2, T3 t3,
                                                    T4 t4) {
        return c.construct(t1, t2, t3, t4);
    }

    public static <R, T1, T2, T3, T4> Pointer<R> of(ConstructorWithFourParams<R, T1, T2, T3, T4> c, T1 t1, T2 t2, T3 t3,
                                                    T4 t4) {
        return of(newInstance(c, t1, t2, t3, t4));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @param t5
     * @return
     */
    public static <R, T1, T2, T3, T4, T5> R newInstance(ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> c, T1 t1,
                                                        T2 t2, T3 t3, T4 t4, T5 t5) {
        return c.construct(t1, t2, t3, t4, t5);
    }

    public static <R, T1, T2, T3, T4, T5> Pointer<R> of(ConstructorWithFiveParams<R, T1, T2, T3, T4, T5> c, T1 t1,
                                                        T2 t2, T3 t3, T4 t4, T5 t5) {
        return of(newInstance(c, t1, t2, t3, t4, t5));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @param t5
     * @param t6
     * @return
     */
    public static <R, T1, T2, T3, T4, T5, T6> R newInstance(ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> c,
                                                            T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return c.construct(t1, t2, t3, t4, t5, t6);
    }

    public static <R, T1, T2, T3, T4, T5, T6> Pointer<R> of(ConstructorWithSixParams<R, T1, T2, T3, T4, T5, T6> c,
                                                            T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return of(newInstance(c, t1, t2, t3, t4, t5, t6));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @param t5
     * @param t6
     * @param t7
     * @return
     */
    public static <R, T1, T2, T3, T4, T5, T6, T7> R newInstance(
            ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
            T7 t7) {
        return c.construct(t1, t2, t3, t4, t5, t6, t7);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7> Pointer<R> of(
            ConstructorWithSevenParams<R, T1, T2, T3, T4, T5, T6, T7> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
            T7 t7) {
        return of(newInstance(c, t1, t2, t3, t4, t5, t6, t7));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @param t5
     * @param t6
     * @param t7
     * @param t8
     * @return
     */
    public static <R, T1, T2, T3, T4, T5, T6, T7, T8> R newInstance(
            ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
            T7 t7, T8 t8) {
        return c.construct(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7, T8> Pointer<R> of(
            ConstructorWithEightParams<R, T1, T2, T3, T4, T5, T6, T7, T8> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6,
            T7 t7, T8 t8) {
        return of(newInstance(c, t1, t2, t3, t4, t5, t6, t7, t8));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param <T9>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @param t5
     * @param t6
     * @param t7
     * @param t8
     * @param t9
     * @return
     */
    public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9> R newInstance(
            ConstructorWithNineParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5,
            T6 t6, T7 t7, T8 t8, T9 t9) {
        return c.construct(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9> Pointer<R> of(
            ConstructorWithNineParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5,
            T6 t6, T7 t7, T8 t8, T9 t9) {
        return of(newInstance(c, t1, t2, t3, t4, t5, t6, t7, t8, t9));
    }

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param <T9>
     * @param <T10>
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @param t5
     * @param t6
     * @param t7
     * @param t8
     * @param t9
     * @param t10
     * @return
     */
    public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> R newInstance(
            ConstructorWithTenParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5,
            T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return c.construct(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    public static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Pointer<R> of(
            ConstructorWithTenParams<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> c, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5,
            T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return of(newInstance(c, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }

    // Now , T[] <=> T...t

    /**
     * instantiate object by parameters-ed constructor
     *
     * @param <R>
     * @param <T>
     * @param c
     * @param t
     * @return
     */
    @SafeVarargs
    public static <R, T> R newInstance(ConstructorWithParams<R, T> c, T... t) {
        return c.construct(t);
    }

    @SafeVarargs
    public static <R, T> Pointer<R> of(ConstructorWithParams<R, T> c, T... t) {
        return of(newInstance(c, t));
    }


    public static String firstUpperCase(String str) {
        String ret = null;
        if (Objects.nonNull(str)) {
            char[] strArr = str.toCharArray();
            if (strArr.length > 0) {
//        		strArr[0] -='a';
//        		strArr[0] += 'A';
//
                if (!Character.isUpperCase(strArr[0])) {
                    strArr[0] = Character.toUpperCase(strArr[0]);
                }
                ret = String.valueOf(strArr);
            }
        }
        return ret;
    }

    public static String firstLowerCase(String str) {
        String ret = null;
        if (Objects.nonNull(str)) {
            char[] strArr = str.toCharArray();
            if (strArr.length > 0) {
//        		strArr[0] -='a';
//        		strArr[0] += 'A';
//
                if (!Character.isLowerCase(strArr[0])) {
                    strArr[0] = Character.toLowerCase(strArr[0]);
                }
                ret = String.valueOf(strArr);
            }
        }
        return ret;
    }

    private static final Scanner SCANNER = new Scanner(System.in);

    public static int readInt() {
        return SCANNER.nextInt();
    }

    public static String readString() {
        return SCANNER.next();
    }
    
    public static List<String> propertiesOfClass(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }


    
}

class Time {
    private long mSec;
    private long sec;
    private long min;
    private long hour;
    private long daysOfMon;
    private long mon;
    private long year;

    private static final long MIN_YEAR = -999999999L;
    private static final long MAX_YEAR = 999999999L;


    public static boolean isLeap(long year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    private static final int DAYS_PER_CYCLE = 146097;

    private static final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

    public void calcTime(long milliSeconds) {
        //遇到负数 通过周期 来计算 f(60+x) = f(x)
        //f(24+x) = f(x)
        //

        mSec = milliSeconds % 1000;
        //总秒数
        long secTotal = milliSeconds / 1000;
        //秒
        sec = secTotal % 60;
        //总分钟数
        long minTotal = secTotal / 60;
        //分
        min = minTotal % 60;
        //总小时数
        long hourTotal = minTotal / 60;
        //时
        hour = hourTotal % 24;

        //计算年月日
        //总天数
        long days = hourTotal / 24;


//        计算年月日
        /**
         * 从0000年到1970,01,01,00:00:00的总天数 + 1970,01,01,00:00:00到如今的总天数
         * =从0000~now的总天数
         */
        long zeroDay = days + DAYS_0000_TO_1970;
        /**
         * 第0000年是闰年？所以1月31天，2月19天，所以60天
         * 从0000年03月01日起到现在的总天数
         * 所以 -60,
         * 为什么要这么做呢？
         * 每四年循环，闰日(不是闰年)都是最后一天？
         * zeroDay:是从0000年03月01日起到现在的总天数
         */
        // find the march-based year
        // adjust to 0000-03-01 so leap day is at end of four year cycle
        zeroDay -= 60;
        /*
         * ?
         */
        long adjust = 0;
        /*
         * 负的天数 公元前？
         */
        if (zeroDay < 0) {

            // adjust negative years to positive for calculation
            long adjustCycles = (zeroDay + 1) / DAYS_PER_CYCLE - 1;
            //adjust 调整的年数 负的！
            adjust = adjustCycles * 400;

            zeroDay += -adjustCycles * DAYS_PER_CYCLE;
        }

        long yearEst = (400 * zeroDay + 591) / DAYS_PER_CYCLE;

        long doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);

        if (doyEst < 0) {
            // fix estimate
            yearEst--;
            doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        }
        // reset any negative year
        yearEst += adjust;
        //剩余天数 注意总天数zeroDay 是从0000年3月1日开始的！
        //剩余天数也是从3月1日开始的?
        int marchDoy0 = (int) doyEst;

        /**
         * 除开1月份，2月份，剩下的月份（5个月份）半年天数是一样的！！！
         * 3    4   5   6   7
         * 31   30  31  30  31
         *
         * 12   11  10  9   8
         * 31   30  31  30  31
         *
         * 31+30+31+30+31 = 153！！！
         *
         * marchDoy0是从3月1日开始的年内的度过天数，如3月1日至8月X日的天数
         * 如果不是整除，也就是浮点数除法,
         * marchDoy0/半年（5个月）天数*5 = 实际月份！！！
         * marchDay0/(153*2)*10 = 实际月份！！！
         * 为什么+2?
         */
        // convert march-based values back to january-based
        int marchMonth0 = (marchDoy0 * 5 + 2) / 153;
        /**
         * 前面的计算都是 从 0000,03,01,00:00:00开始的，算的都是400的闰年整数倍
         * 所以必须算的月份加上2个月
         * 因为月份只有12个月，所以要模运算，取余运算
         * marchMonth0算的1年的后10月的月份，所以要+2
         */
        mon = (marchMonth0 + 2) % 12 + 1;
        /**
         * 半年（5个月）153
         * 306=153+153
         * 也就是3,4,5,6,7加上8,9,10,11,12 月份的天数.
         * 1年内从3月1日开始的度过天数(1年内后10个月内)marchDoy0 - 1年后10个月的月份第marchMonth0月 / 10 * 306的天数 = 下个月内度过的天数
         * 1年内从3月1日开始的度过天数 如yearEst 是 1969，那marchDoy0就是1970从3月1日开始的度过天数，当然后yearEst会被adjust,月份计算纠正为1970年的！
         * 下个月内度过的天数 就是 就是月内的天数 daysOfMon!!!
         */
        daysOfMon = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1;
        //大于等于10 就得 +1年(具体以计算为准)，也就是下一年了！
        yearEst += marchMonth0 / 10;

        // check year now we are certain it is correct
        if (yearEst < MIN_YEAR || yearEst > MAX_YEAR) {
            throw new DateTimeException("Invalid value for year (valid values " + this + "): " + yearEst);
        }
        year = yearEst;
    }

}