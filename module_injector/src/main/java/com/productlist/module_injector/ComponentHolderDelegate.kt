package com.productlist.module_injector

import java.lang.ref.WeakReference

/**
 * Delegation class for a component holder.
 *
 * The current implementation is based on the publication:
 * https://proandroiddev.com/modularization-of-android-applications-with-lazy-initialization-a091eaaa284a
 *
 * Default usage example:
 * object MyFeatureComponentHolder :
 *   ComponentHolder<MyFeatureFeatureApi, MyFeatureFeatureDependencies> {
 *       private val componentHolderDelegate = ComponentHolderDelegate<
 *           MyFeatureFeatureApi, // Feature API interface
 *           MyFeatureFeatureDependencies, // Feature dependencies interface
 *           MyFeatureComponent // Feature component
 *           > { dependencies: MyFeatureFeatureDependencies ->
 *               MyFeatureComponent.initAndGet(dependencies) // Component creation.
 *           }
 *
 *       // Returns "injector" interface for internal usage in the module.
 *       internal fun getInjector(): MyFeatureInjector = componentHolderDelegate.getComponentImpl()
 *
 *       // Dependency provider
 *       override var dependencyProvider: (() -> MyFeatureFeatureDependencies)? by componentHolderDelegate::dependencyProvider
 *
 *       // Returns feature API interface to be used by other modules.
 *       override fun get(): MyFeatureFeatureApi = componentHolderDelegate.getComponentImpl()
 *   }
 *
 */
class ComponentHolderDelegate<A : BaseFeatureAPI, D : BaseFeatureDependencies, C : A>(
    private val componentFactory: (D) -> C
) : ComponentHolder<A, D> {

    override var dependencyProvider: (() -> D)? = null

    private var componentWeakRef: WeakReference<C>? = null

    fun getComponentImpl(): C {
        var component: C? = null

        synchronized(this) {
            dependencyProvider?.let { dependencyProvider ->
                component = componentWeakRef?.get()
                if (component == null) {
                    component = componentFactory(dependencyProvider())
                    componentWeakRef = WeakReference(component)
                }
            } ?: throw IllegalStateException("dependencyProvider for component with factory $componentFactory is not initialized")
        }

        return component
            ?: throw IllegalStateException("Component holder with component factory $componentFactory is not initialized")
    }

    override fun get(): A {
        return getComponentImpl()
    }
}
