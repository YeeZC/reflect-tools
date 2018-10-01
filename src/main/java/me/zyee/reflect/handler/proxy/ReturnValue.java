package me.zyee.reflect.handler.proxy;

/**
 * @author yee
 * @date 2018/7/25
 */
class ReturnValue {
    static <T> Value<T> value(T value) {
        return new Value<T>(value, Type.NORMAL);
    }

    static Value<String> out(String msg) {
        return new Value<String>(msg, Type.OUTPUT);
    }

    enum Type {
        NORMAL, OUTPUT
    }

    static class Value<T> {
        T value;
        Type type;

        public Value(T value, Type type) {
            this.value = value;
            this.type = type;
        }
    }
}
