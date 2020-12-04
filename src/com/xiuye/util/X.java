package com.xiuye.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 新值 返回 新对象X
 * 旧值 返回 this
 * V 返回 this
 * R 返回 新对象X
 *
 * @param <T>
 */
public final class X<T> {
    private static final X<?> EMPTY = new X<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final T value;

    private X() {
        this.value = null;
    }

    public static <T> X<T> empty() {
        @SuppressWarnings("unchecked")
        X<T> t = (X<T>) EMPTY;
        return t;
    }


    private X(T value) {
//        this.value = Objects.requireNonNull(value);
        this.value = value;
    }

    public static <T> X<T> of(T value) {
        return new X<>(value);
    }

    public static <T> X<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public static <U, I> U cast(I in) {
        return (U) in;
    }

    public static <U> List<U> list() {
        return new ArrayList<>();
    }

    public static <U> X<List<U>> list(Class<U> uClass) {
        return of(list());
    }

    public static <U> X<Set<U>> set(Class<U> uClass) {
        return of(set());
    }

    public static <U> Set<U> set() {
        return new HashSet<>();
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<>();
    }

    public static void throwREx(String msg) {
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

    public static <U, E extends Collection<U>> X<U> nonEmptyV(E collection, Consumer<E> consumer) {
        if (nonEmpty(collection)) {
            consumer.accept(collection);
        }
        return empty();
    }

    public static <U, E extends Collection<U>, R> X<R> nonEmptyV(E collection, Function<E, R> function) {
        if (nonEmpty(collection)) {
            return ofNullable(function.apply(collection));
        }
        return empty();
    }

    public static <U, E extends Collection<U>> X<E> isEmptyV(E collection, Runnable runnable) {
        if (isEmpty(collection)) {
            runnable.run();
        }
        return ofNullable(collection);
    }

    public static <U, E extends Collection<U>> X<E> isEmptyR(E collection, Supplier<E> supplier) {
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


    public X<T> ifPresentV(Consumer<? super T> action) {
        if (isPresent()) {
            action.accept(value);
        }
        return this;
    }

    public <U> X<U> ifPresentR(Function<? super T, ? extends U> mapper) {
        if (isPresent()) {
            return ofNullable(mapper.apply(value));
        }
        return empty();
    }

    public X<T> ifAbsentV(Runnable runnable) {
        if (isAbsent()) {
            runnable.run();
        }
        return this;
    }

    public X<T> ifAbsentR(Supplier<? extends T> supplier) {
        if (isAbsent()) {
            return ofNullable(supplier.get());
        }
        return this;
    }

    public X<T> ifPresentOrElseV(Consumer<? super T> action, Runnable emptyAction) {
        if (isPresent()) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
        return this;
    }

    public <U> X<U> ifPresentOrElseR(Function<? super T, ? extends U> mapper, Supplier<U> emptyAction) {
        if (isPresent()) {
            return ofNullable(mapper.apply(value));
        } else {
            return ofNullable(emptyAction.get());
        }
    }

    public X<T> ifAbsentOrElseV(Runnable emptyAction, Consumer<? super T> action) {
        if (isAbsent()) {
            emptyAction.run();
        } else {
            action.accept(value);
        }
        return this;
    }

    public <U> X<U> ifAbsentOrElseR(Supplier<U> emptyAction, Function<? super T, ? extends U> mapper) {
        if (isAbsent()) {
            return ofNullable(emptyAction.get());
        } else {
            return ofNullable(mapper.apply(value));
        }
    }

    public X<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    public <U> X<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return X.ofNullable(mapper.apply(value));
        }
    }

    public <U> X<U> flatMap(Function<? super T, ? extends X<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            X<U> r = (X<U>) mapper.apply(value);
            return Objects.requireNonNull(r);
        }
    }

    public X<T> or(Supplier<? extends X<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            X<T> r = (X<T>) supplier.get();
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

        if (!(obj instanceof X)) {
            return false;
        }

        X<?> other = (X<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return isPresent()
                ? String.format("X[%s]", value)
                : "X.empty";
    }

    //this my code
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

    public X<T> logItself() {
        log(value);
        return this;
    }

    public X<T> errItself() {
        err(value);
        return this;
    }


    public X<T> printItself() {
        print(value);
        return this;
    }


    public X<T> printlnItself() {
        println(value);
        return this;
    }

    public X<T> lgItself() {
        lg(value);
        return this;
    }

    public X<String> toJson() {
        return ofNullable(gson.toJson(Objects.requireNonNull(value)));
    }

    public <U> X<U> toObject(Class<U> clazz) {
        Objects.requireNonNull(value);
        if (value instanceof Reader) {
            Reader json = cast(value);
            return ofNullable(gson.fromJson(json, clazz));
        } else if (value instanceof JsonElement) {
            Reader json = cast(value);
            return ofNullable(gson.fromJson(json, clazz));
        }
        //default string
        String json = cast(value);

        return ofNullable(gson.fromJson(json, clazz));
    }


    @SafeVarargs
    public static <N> X<N[]> log(N... t) {
        if (t.length == 0)
            System.out.println();
        else {
            for (int i = 0; i < t.length - 1; i++) {
                System.out.print(t[i] + " ");
            }
            System.out.println(t[t.length - 1]);
        }
        return ofNullable(t);
    }


    @SafeVarargs
    public static <N> X<N[]> err(N... t) {
        if (t.length == 0)
            System.err.println();
        else {
            for (int i = 0; i < t.length - 1; i++) {
                System.err.print(t[i] + " ");
            }
            System.err.println(t[t.length - 1]);
        }
        return ofNullable(t);
    }


    @SafeVarargs
    public static <N> X<N[]> print(N... ts) {
        if (ts.length > 0) {
            for (int i = 0; i < ts.length - 1; i++) {
                System.out.print(ts[i] + " ");
            }
            System.out.print(ts[ts.length - 1]);
        }
        return ofNullable(ts);
    }


    @SafeVarargs
    public static <N> X<N[]> println(N... t) {
        return log(t);
    }

    @SafeVarargs
    public static <N> X<N[]> lg(N... t) {
        return log(t);
    }


    public <U> X<U> thenR(Function<? super T, ? extends U> thenPhase) {
        Objects.requireNonNull(thenPhase);
        return ofNullable(thenPhase.apply(value));
    }

    public X<T> thenV(Consumer<? super T> thenPhase) {
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

    // I/O
    private static Scanner scanner = new Scanner(System.in);

    // \n eof
    public static int readInt() {
        return scanner.nextInt();
    }

}
