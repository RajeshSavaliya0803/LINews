package com.example.linews.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class NewsApiResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem> = listOf(),

	@field:SerializedName("status")
	val status: String? = null
)


data class Source(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val sourceId: String? = null
)


@Entity(tableName = "article")
data class ArticlesItem(

	@PrimaryKey(autoGenerate = true)
	val id: Int? = null,

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@Embedded
	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
