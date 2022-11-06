/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.common.di.componentmanager;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Stores Dagger components.
 */
public class ComponentsStore {

    /**
     * Key is component class. Value is component instance.
     */
    private final Map<Class<?>, Object> componentsMap = new HashMap<>();

    public ComponentsStore() {
    }

    /**
     * Adds Dagger component in {@link #componentsMap}.
     *
     * @param component Dagger component to be added.
     * @param <T>       Type of component to be added.
     */
    public <T> void addComponent(T component) {
        Class<?> clazz = getComponentInterface(component);
        componentsMap.put(clazz, component);
    }

    /**
     * Removes Dagger component from {@link #componentsMap}.
     *
     * @param clazz Java class of Dagger component to be removed.
     */
    public void remove(Class<?> clazz) {
        componentsMap.remove(clazz);
    }

    /**
     * Checks is Dagger component contains in {@link #componentsMap}.
     *
     * @param clazz Java class of Dagger component to be removed.
     * @return True, if Dagger component contains in {@link #componentsMap}.
     */
    public boolean hasComponent(Class<?> clazz) {
        return componentsMap.containsKey(clazz);
    }

    /**
     * @param clazz Java class of Dagger component to be got.
     * @param <T>   Type of component to be got.
     * @return Instance of clazz.
     * @throws IllegalStateException If component is not initialized.
     */
    public <T> T getComponent(Class<T> clazz) {
        Object component = componentsMap.get(clazz);
        if (component == null) {
            throw new IllegalStateException(
                    "Component " + clazz.getSimpleName() + " is not initialized");
        }

        return clazz.cast(component);
    }

    /**
     * Removes all Dagger components from {@link #componentsMap}.
     */
    public void clear() {
        componentsMap.clear();
    }

    /**
     * Dagger generates ComponentImpl, so we should get an interface
     * that is marked {@link Component} in your project.
     *
     * @return Component or Subcomponent class
     * @throws IllegalArgumentException if argument is not a Dagger component
     */
    @NonNull
    private <T> Class<?> getComponentInterface(T component) {
        Class<?> implClass = component.getClass();
        Class<?>[] interfaces = implClass.getInterfaces();
        if (interfaces.length < 1) {
            throw new IllegalArgumentException(
                    "Argument is not a component: " + implClass.getName());
        }

        Class<?> clazz = interfaces[0];
        if (!clazz.isAnnotationPresent(Subcomponent.class) &&
                !clazz.isAnnotationPresent(Component.class)) {
            throw new IllegalArgumentException("Argument is not a component: " + implClass.getName());
        }
        return clazz;
    }
}