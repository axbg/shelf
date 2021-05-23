self.addEventListener("install", e => {
  e.waitUntil(
    caches.open("shelf-cache").then(function(cache) {
      return cache.addAll([
        "/offline.html"
      ]);
    })
  );
});

self.addEventListener("fetch", event => {
  if(!event.request.url.includes("api")) {
    event.respondWith(
      fetch(event.request).catch(() => {
        return caches.match("/offline.html");
      }),
    );
  }
});
