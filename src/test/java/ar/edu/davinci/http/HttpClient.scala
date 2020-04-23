package ar.edu.davinci.http

import ar.edu.davinci.domain.dto.ResponseError
import ar.edu.davinci.infraestructure.exception.FitmeException
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3._

import scala.util.{Failure, Success, Try}

class HttpClient[T](endpoint: String, token: String) {

  private val httpClient = new OkHttpClient()
  private val JSON = MediaType.get("application/json; charset=utf-8")
  private val objectMapper = new GsonBuilder().serializeNulls.create
  val typeGeneric = new TypeToken[T]() {}.getType()


  def get[T](path: String) = {
    val request = new Request.Builder()
      .url(s"$endpoint$path")
      .addHeader("Authorization", token)
      .build()

    doRequest[T](request)
  }

  def post[T](path: String, entity: T) = {
    val body = RequestBody.create(JSON, objectMapper.toJson(entity))

    val request = new Request.Builder()
      .url(s"$endpoint$path")
      .post(body)
      .addHeader("Authorization", token)
      .addHeader("Content-Type", "application/json")
      .build()

    doRequest[T](request)
  }

  private def doRequest[T](request: Request) = {
    Try(httpClient.newCall(request).execute()) match {
      case Success(response: Response) => handleResponse[T](response)
      case Failure(error) => throw error
    }
  }

  private def handleResponse[T](response: Response) = {
    if (response.isSuccessful)
      Success(objectMapper.fromJson[T](response.body().string(), typeGeneric))
    else {
      val error = objectMapper.fromJson(response.body().string(), classOf[ResponseError])
      Failure(throw new FitmeException(error.getDescription))
    }
  }

}
