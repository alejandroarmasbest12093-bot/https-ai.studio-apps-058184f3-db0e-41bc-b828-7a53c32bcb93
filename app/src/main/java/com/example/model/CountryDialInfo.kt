package com.example.model

/**
 * Información de países y prefijos internacionales para llamadas con el Chip Satelital Armas.
 * Permite marcar a cualquier número de cualquier país del mundo con enrutamiento soberano Direct-to-Cell.
 */
data class CountryDialInfo(
  val countryCode: String,
  val dialCode: String,
  val name: String,
  val flagEmoji: String,
  val placeholderNumber: String,
  val exampleFullNumber: String
) {
  val isoCode: String get() = countryCode
}

object ArmasCountryDirectory {
  val DEFAULT_COUNTRY = CountryDialInfo(
    countryCode = "ARMAS",
    dialCode = "+999",
    name = "Satélite Armas Global",
    flagEmoji = "🌐",
    placeholderNumber = "ARMAS-770-001",
    exampleFullNumber = "+999 ARMAS-770-001"
  )

  val COUNTRIES: List<CountryDialInfo> = listOf(
    DEFAULT_COUNTRY,
    CountryDialInfo("MX", "+52", "México", "🇲🇽", "55 1234 5678", "+52 55 1234 5678"),
    CountryDialInfo("US", "+1", "Estados Unidos", "🇺🇸", "202 555 0143", "+1 202 555 0143"),
    CountryDialInfo("CA", "+1", "Canadá", "🇨🇦", "416 555 0199", "+1 416 555 0199"),
    CountryDialInfo("ES", "+34", "España", "🇪🇸", "612 345 678", "+34 612 345 678"),
    CountryDialInfo("CO", "+57", "Colombia", "🇨🇴", "300 123 4567", "+57 300 123 4567"),
    CountryDialInfo("AR", "+54", "Argentina", "🇦🇷", "11 2345 6789", "+54 11 2345 6789"),
    CountryDialInfo("VE", "+58", "Venezuela", "🇻🇪", "412 123 4567", "+58 412 123 4567"),
    CountryDialInfo("CL", "+56", "Chile", "🇨🇱", "9 1234 5678", "+56 9 1234 5678"),
    CountryDialInfo("PE", "+51", "Perú", "🇵🇪", "912 345 678", "+51 912 345 678"),
    CountryDialInfo("EC", "+593", "Ecuador", "🇪🇨", "99 123 4567", "+593 99 123 4567"),
    CountryDialInfo("BR", "+55", "Brasil", "🇧🇷", "11 91234 5678", "+55 11 91234 5678"),
    CountryDialInfo("GT", "+502", "Guatemala", "🇬🇹", "5123 4567", "+502 5123 4567"),
    CountryDialInfo("PA", "+507", "Panamá", "🇵🇦", "6123 4567", "+507 6123 4567"),
    CountryDialInfo("CR", "+506", "Costa Rica", "🇨🇷", "8123 4567", "+506 8123 4567"),
    CountryDialInfo("DO", "+1809", "República Dominicana", "🇩🇴", "809 555 0123", "+1809 555 0123"),
    CountryDialInfo("UY", "+598", "Uruguay", "🇺🇾", "91 234 567", "+598 91 234 567"),
    CountryDialInfo("PY", "+595", "Paraguay", "🇵🇾", "981 123 456", "+595 981 123 456"),
    CountryDialInfo("BO", "+591", "Bolivia", "🇧🇴", "7123 4567", "+591 7123 4567"),
    CountryDialInfo("GB", "+44", "Reino Unido", "🇬🇧", "7911 123456", "+44 7911 123456"),
    CountryDialInfo("FR", "+33", "Francia", "🇫🇷", "6 12 34 56 78", "+33 6 12 34 56 78"),
    CountryDialInfo("DE", "+49", "Alemania", "🇩🇪", "151 23456789", "+49 151 23456789"),
    CountryDialInfo("IT", "+39", "Italia", "🇮🇹", "312 345 6789", "+39 312 345 6789"),
    CountryDialInfo("PT", "+351", "Portugal", "🇵🇹", "912 345 678", "+351 912 345 678"),
    CountryDialInfo("CH", "+41", "Suiza", "🇨🇭", "78 123 45 67", "+41 78 123 45 67"),
    CountryDialInfo("JP", "+81", "Japón", "🇯🇵", "90 1234 5678", "+81 90 1234 5678"),
    CountryDialInfo("CN", "+86", "China", "🇨🇳", "138 0000 0000", "+86 138 0000 0000"),
    CountryDialInfo("KR", "+82", "Corea del Sur", "🇰🇷", "10 1234 5678", "+82 10 1234 5678"),
    CountryDialInfo("IN", "+91", "India", "🇮🇳", "98765 43210", "+91 98765 43210"),
    CountryDialInfo("AU", "+61", "Australia", "🇦🇺", "412 345 678", "+61 412 345 678"),
    CountryDialInfo("AE", "+971", "Emiratos Árabes", "🇦🇪", "50 123 4567", "+971 50 123 4567"),
    CountryDialInfo("SA", "+966", "Arabia Saudita", "🇸🇦", "50 123 4567", "+966 50 123 4567"),
    CountryDialInfo("IL", "+972", "Israel", "🇮🇱", "50 123 4567", "+972 50 123 4567"),
    CountryDialInfo("RU", "+7", "Rusia", "🇷🇺", "912 345 67 89", "+7 912 345 67 89"),
    CountryDialInfo("UA", "+380", "Ucrania", "🇺🇦", "50 123 4567", "+380 50 123 4567"),
    CountryDialInfo("ZA", "+27", "Sudáfrica", "🇿🇦", "82 123 4567", "+27 82 123 4567")
  )

  val ALL_COUNTRIES: List<CountryDialInfo> get() = COUNTRIES

  /**
   * Detecta el país correspondiente a un número telefónico marcado.
   */
  fun detectCountry(number: String): CountryDialInfo {
    val clean = number.trim()
    if (clean.startsWith("+999") || clean.contains("ARMAS")) return DEFAULT_COUNTRY

    // Ordenamos por longitud de dialCode descendente para empatar prefijos largos primero (ej. +1809 antes de +1)
    val sorted = COUNTRIES.filter { it.dialCode != "+999" }.sortedByDescending { it.dialCode.length }
    for (country in sorted) {
      if (clean.startsWith(country.dialCode) || clean.startsWith(country.dialCode.removePrefix("+"))) {
        return country
      }
    }
    return DEFAULT_COUNTRY
  }
}
