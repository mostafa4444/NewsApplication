package com.task.news.local.persistance
import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.news.model.response.news.Article
import kotlinx.coroutines.flow.Flow

/*
    Room Database DAO class which contain all queries
 */
@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleToDatabase(article: Article): Long

    @Query("SELECT * FROM Article")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}