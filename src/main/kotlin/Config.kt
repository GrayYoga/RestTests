object Config {

    private fun property(name: String, default: String): String {
        return System.getProperty(name) ?: System.getenv(name) ?: default
    }

    val baseUrl = property("BASE_URL", "http://localhost:8080")
}
