package js.training.kopring.model.mapper

import org.hibernate.Hibernate
import org.mapstruct.Condition

class LazyLoadingMappingConfig {

    @Condition
    fun isNotLazyLoaded(source: Collection<*>?): Boolean {
        if (source == null) return false
        return Hibernate.isInitialized(source)
    }
}