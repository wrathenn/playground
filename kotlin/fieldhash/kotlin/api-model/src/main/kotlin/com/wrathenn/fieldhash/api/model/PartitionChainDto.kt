package com.wrathenn.fieldhash.api.model

import arrow.core.NonEmptyList
import arrow.core.serialization.NonEmptyListSerializer
import arrow.core.serialization.NonEmptySetSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PartitionChainNodeDto(@SerialName("incidentType") val type: Type) {
  enum class Type {
    DEEPEN,
    EXPAND,
  }

  @Serializable
  data class Deepening(
    val partition: PartitionDto,
  ): PartitionChainNodeDto(Type.DEEPEN)

  @Serializable
  data class Expanding(
    @Serializable(with = NonEmptyListSerializer::class)
    val partitions: NonEmptyList<PartitionDto>,
  ): PartitionChainNodeDto(Type.EXPAND)
}

@Serializable
data class PartitionChainDto(
  val id: Long,
  @Serializable(with = NonEmptyListSerializer::class)
  val nodes: NonEmptyList<PartitionChainNodeDto>,
)

data class PartitionChainInsertableDto(
  @Serializable(with = NonEmptyListSerializer::class)
  val nodes: NonEmptyList<PartitionChainNodeDto>,
)

@Serializable
data class PartitionFormDto(
  val xCount: Long,
  val yCount: Long,
  val merges: List<@Serializable(with = NonEmptySetSerializer::class) MergeDto>,
)

@Serializable
data class PartitionDto(
  val xCount: Long,
  val yCount: Long,
  val coordsMap: Map<CoordsDto, String>,
  val backing: PartitionFormDto? = null,
)
