package com.wrathenn.fieldhash.api.model

import arrow.core.NonEmptySet
import kotlinx.serialization.Serializable

typealias MergeDto = NonEmptySet<@Serializable CoordsDto>