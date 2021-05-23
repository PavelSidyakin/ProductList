package com.productlist.module_injector

/**
 * Base interfaces for modules API, dependencies and Component Holder.
 *
 * The current implementation is based on the publication:
 * https://proandroiddev.com/modularization-of-android-applications-with-lazy-initialization-a091eaaa284a
 *
 */
interface BaseFeatureDependencies {
    val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies>
}

interface BaseFeatureAPI

interface ComponentHolder<A : BaseFeatureAPI, D : BaseFeatureDependencies> {
    var dependencyProvider: (() -> D)?
    fun get(): A
}

