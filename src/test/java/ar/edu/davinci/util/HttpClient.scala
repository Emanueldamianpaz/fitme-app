package ar.edu.davinci.util

import com.google.gson.Gson
import okhttp3._

import scala.util.{Failure, Success, Try}

class HttpClient(endpoint: String, token: String) {

  private val httpClient = new OkHttpClient()
  private val JSON = MediaType.get("application/json; charset=utf-8")
  private val objectMapper = new Gson()

  def get[T](path: String)(implicit m: Manifest[T]) = {
    val request = new Request.Builder()
      .url(s"$endpoint$path")
      .addHeader("Authorization", token)
      .build()

    doRequest[T](request)
  }

  def post[T](path: String, entity: T)(implicit m: Manifest[T]) = {
    val body = RequestBody.create(JSON, objectMapper.toJson(entity))

    val request = new Request.Builder()
      .url(s"$endpoint$path")
      .post(body)
      .addHeader("Authorization", token)
      .addHeader("Content-Type", "application/json")
      .build()

    doRequest[T](request)
  }

  private def doRequest[T](request: Request)(implicit m: Manifest[T]) = {
    Try(httpClient.newCall(request).execute()) match {
      case Success(response: Response) => handleResponse[T](response)
      case Failure(_) => Left(ResponseResult.FAILURE)
    }
  }

  private def handleResponse[T](response: Response)(implicit m: Manifest[T]) = {
    if (response.isSuccessful)
      Right(ResponseResult.SUCCESS)
    else
      Left(ResponseResult.FAILURE)
  }
}

object ResponseResult extends Enumeration {
  val SUCCESS, FAILURE = Value
}
