package location

interface CountryCodeManager {
    fun getCountryCode(): String?
}

expect fun getCountryCodeManager(): CountryCodeManager