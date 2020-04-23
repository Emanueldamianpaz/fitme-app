package ar.edu.davinci.http.entities

class EntitiesWithLink[T](listEntity: List[T], link: String) {

  def apply(listEntity: List[T], link: String) = new EntitiesWithLink[T](listEntity, link)

  def getListEntity: List[T] = listEntity

  def getLink: String = link

}
