File base = new File(request.outputDirectory, request.artifactId)
File pr = new File(base, "project-root")

def move = { String from, String to ->
    File src = new File(pr, from)
    if (src.exists()) {
        File dst = new File(base, to)
        dst.parentFile?.mkdirs()
        if (dst.exists()) dst.delete()
        src.renameTo(dst)
        println "[post-generate] moved ${from} -> ${to}"
    } else {
        println "[post-generate] missing: " + src.absolutePath
    }
}

if (pr.exists()) {
    move("README.md", "README.md")
    move("docker-compose.yml", "docker-compose.yml")
    move(".env", ".env")
    pr.delete()
} else {
    println "[post-generate] project-root not found at: " + pr.absolutePath
}
