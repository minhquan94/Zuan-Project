/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.parser.codehaus.preon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zuan.parser.codehaus.preon.Builder;
import com.zuan.parser.codehaus.preon.Codec;
import com.zuan.parser.codehaus.preon.Resolver;
import com.zuan.parser.codehaus.preon.buffer.BitBuffer;

/**
 * A general purpose 'marker' annotation, indicating that a certain field is
 * considered is expected to be retrieved from a {@link BitBuffer} when invoking
 * {@link Codec#decode(org.codehaus.preon.buffer.BitBuffer, Resolver, Builder)}.
 * <p/>
 * <p>
 * This annotation is the easy way to mark fields as 'serializable'. When this
 * annotation is used, the processing expectations are that the {@link Codec}
 * will use the default settings for decoding values of the type of field
 * involved. If the defaults don't suffice, then the other annotations come into
 * play.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Bound {

}
