package com.ancient.agent.core.builder;

import com.ancient.agent.core.context.CustomContextAccessor;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.jar.asm.Opcodes;

public class TargetObjectInterceptorBuilder implements InterceptorBuilder {

    private static final String EXTRA_DATA = "_$EXTRA_DATA$_";

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder) {
//        return builder.defineField(
//                        EXTRA_DATA, Object.class, Opcodes.ACC_PRIVATE | Opcodes.ACC_VOLATILE)
//                .implement(CustomContextAccessor.class)
//                .intercept(FieldAccessor.ofField(EXTRA_DATA));
        return builder.defineField(EXTRA_DATA, Object.class).implement(CustomContextAccessor.class).intercept(FieldAccessor.ofField(EXTRA_DATA));
    }

}
