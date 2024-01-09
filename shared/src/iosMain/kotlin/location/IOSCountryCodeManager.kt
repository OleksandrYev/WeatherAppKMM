package location

import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleCountryCode
import platform.Foundation.currentLocale

class IOSCountryCodeManager : CountryCodeManager {
    override fun getCountryCode(): String =
        NSLocale.currentLocale().objectForKey(NSLocaleCountryCode).toString()
}

actual fun getCountryCodeManager(): CountryCodeManager = IOSCountryCodeManager()