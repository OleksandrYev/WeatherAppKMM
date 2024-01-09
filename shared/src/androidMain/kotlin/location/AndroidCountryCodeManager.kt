package location

import java.util.Locale

class AndroidCountryCodeManager : CountryCodeManager {
    override fun getCountryCode(): String? = Locale.getDefault().country
}

actual fun getCountryCodeManager(): CountryCodeManager = AndroidCountryCodeManager()