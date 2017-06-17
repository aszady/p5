# P5
P5 is a scalable and reliable service for providing time- and position- accurate information about current weather and air conditions, built on basis of state-of-the-art microservies architecture.

P5 uses unique solution named *Crodis*, which provides consistent representation of data in system. Everyone communicate using Crodises. We forward Crodises, consume them, and produce out of nothing. Crodis is an essence of P5. P5 is an outstanding living example of a successful Crodis Platform implementation.

# Services
Services run on following ports:

## Obliczajkas
* `4421` – Obliczajka (model #1)

**API**
* Please call:
`GET /point?lat={}&lon={}`
– to get your *Crodis*

## YACS
* `4200` – main database


**API**
* Please call:
`GET /?latitude={}&longitude={}`
– to get your *Crodis*
* Please call:
`PUT /`
– to make your *Crodis* safe

## Updater
* `5001` – main updater

## Translators
* `4411` – Airly translator – returns *Crodis*
* `4412` – ICM translator – returns *Crodis*
* `4413` – Marasm *pseudo*translator – returns *Crodis*

**API**
* Please call:
`GET /area?latitude={}&longitude={}&radius={}`
– to get your *Crodis*
Note that `latitude` and `longitude`, and `radius` [in km] are just a hint for the Translator. It may, on its own discretion, return any *Crodis*.



