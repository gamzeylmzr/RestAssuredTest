# Rest Assured Test

Rest Assured, Junit, JsonPath, Hamcrest, Jackson Databind ve Log4j kullanılarak geliştirilmiştir.

# Proje içeriği:
Rest Assured BDD yaklaşımı kullanılmıştır.
5 tane get sorgusu ve 1 tane post sorgusu içermektedir.
Post sorgusunda hashmap kullanılmıştır.
Get sorgularından birinde, TestNG deki dataprovider a karşılık gelen parameters kullanılarak birden fazla data ile get sorgusu yapılmaktadır.
Farklı bir get sorgusunda ise JsonPath kullanılarak bir önceki response dan dönen değer diğer bir get sorgusuna parametre olarak gönderilmiştir.

NOT: Junit suite eklenmiştir. Bu class kullanılarak tüm methodlar koşturulabilir.

