/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.common.di.componentmanager;

import android.app.Application;

import androidx.annotation.NonNull;

import com.incetro.projecttemplate.common.di.app.AppComponent;
import com.incetro.projecttemplate.common.di.app.DaggerAppComponent;
import com.incetro.projecttemplate.common.di.app.module.AppModule;


/**
 * Manages all Dagger components.
 */
public class ComponentsManager implements ComponentProvider {

    private static volatile ComponentsManager INSTANCE;
    private final ComponentsStore componentsStore = new ComponentsStore();
    private final AppComponent appComponent;

    private ComponentsManager(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    /**
     * Initialises {@link ComponentManager}
     *
     * @param application {@link Application} instance for init {@link AppComponent}.
     */
    public static synchronized void init(Application application) {
        if (INSTANCE != null) {
            throw new IllegalStateException("ComponentsManager has already been initialized");
        }

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();

        INSTANCE = new ComponentsManager(appComponent);
    }

    public static ComponentsManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ComponentsManager hasn't been initialized");
        }
        return INSTANCE;
    }

    /**
     * @return {@link AppComponent} instance.
     */
    public AppComponent getApplicationComponent() {
        return appComponent;
    }

    /**
     * Adds dagger component in {@link ComponentsStore}.
     *
     * @param <T> Type of component to be added.
     */
    public <T> T addComponent(T component) {
        componentsStore.addComponent(component);
        return component;
    }

    /**
     * Checks is Dagger component contains in {@link ComponentsStore}.
     *
     * @param clazz Java class of Dagger component to be removed.
     * @return True, if Dagger component contains in {@link ComponentsStore}.
     */
    public boolean hasComponent(Class<?> clazz) {
        return componentsStore.hasComponent(clazz);
    }

    /**
     * Removes Dagger component from {@link ComponentsStore}.
     *
     * @param clazz Java class of Dagger component to be removed.
     */
    public void removeComponent(Class<?> clazz) {
        componentsStore.remove(clazz);
    }

    /**
     * Returns Dagger component.
     *
     * @param clazz Java class of Dagger component to be got.
     * @param <T>   Type of Dagger component to be got.
     * @return Dagger component.
     */
    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public <T> T getComponent(@NonNull Class<T> clazz) {
        if (clazz == AppComponent.class) {
            return clazz.cast(getApplicationComponent());
        }

        return componentsStore.getComponent(clazz);
    }
}