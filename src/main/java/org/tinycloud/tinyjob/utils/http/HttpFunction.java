package org.tinycloud.tinyjob.utils.http;

/**
 * 定义函数式接口
 * @param <A> 参数A
 * @param <B> 参数B
 * @param <C> 参数C
 * @param <R> 结果R
 */
@FunctionalInterface
public interface HttpFunction<A, B, C, R> {
    R apply(A a, B b, C c) throws Exception;
}
