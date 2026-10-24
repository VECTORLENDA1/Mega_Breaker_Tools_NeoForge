package com.exoticmoss.megabreakertools.util;

import java.util.function.Supplier;

/// A registered object (like the DeferredHolder of NeoForge): `.get()` gives the object.
public final class RegistryHolder<T> implements Supplier<T> {
    private final T value;

    public RegistryHolder(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }
}
