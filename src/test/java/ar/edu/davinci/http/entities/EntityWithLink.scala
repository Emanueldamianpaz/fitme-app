package ar.edu.davinci.http.entities

class EntityWithLink[T](entity: T, link: String) {

  def apply(entity: T, link: String) = new EntityWithLink[T](entity, link)

  def getEntity: T = entity

  def getLink: String = link

}
