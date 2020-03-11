self.addEventListener("install", (e) => {
 e.waitUntil(
   caches.open('shelf').then(function(cache) {
     return cache.addAll([
       '/offline.html'
     ]);
   })
 );
});

self.addEventListener('fetch', (event) => {
  if (event.request.mode === 'navigate') {
    event.respondWith((async () => {
      try {
        const preloadResponse = await event.preloadResponse;
        if (preloadResponse) {
          return preloadResponse;
        }

        const networkResponse = await fetch(event.request);
        return networkResponse;
      } catch (error) {
        const cache = await caches.open('shelf');
        const cachedResponse = await cache.match('/offline.html');
        return cachedResponse;
      }
    })());
  }
});