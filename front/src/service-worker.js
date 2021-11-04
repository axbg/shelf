self.addEventListener("install", (e) => {
  e.waitUntil(
    caches.open("shelf-cache").then(function (cache) {
      return cache.addAll([
        "/offline.html",
        "/index.html",
        "/share.html",
        "/assets/material-fonts.css",
        "/img/icons/apple/apple-touch-icon-iphone-60x60.png",
        "/img/icons/apple/apple-touch-icon-ipad-76x76.png",
        "/img/icons/apple//apple-touch-icon-iphone-retina-120x120.png",
        "/img/icons/apple/apple-touch-icon-ipad-retina-152x152.png",
        "/img/icons/android/android-launchericon-512-512.png",
        "/img/icons/android/android-launchericon-192-192.png",
        "/img/icons/android/android-launchericon-144-144.png",
        "/img/icons/android/android-launchericon-96-96.png",
        "/img/icons/android/android-launchericon-72-72.png",
        "/img/icons/android/android-launchericon-48-48.png",
        "/img/icons/chrome/chrome-extensionmanagementpage-48-48.png",
        "/img/icons/chrome/chrome-favicon-16-16.png",
        "/img/icons/chrome/chrome-installprocess-128-128.png",
        "/img/icons/firefox/firefox-marketplace-512-512.png",
        "/img/icons/firefox/firefox-marketplace-128-128.png",
        "/img/icons/firefox/firefox-general-256-256.png",
        "/img/icons/firefox/firefox-general-128-128.png",
        "/img/icons/firefox/firefox-general-90-90.png",
        "/img/icons/firefox/firefox-general-64-64.png",
        "/img/icons/firefox/firefox-general-48-48.png",
        "/img/icons/firefox/firefox-general-32-32.png",
        "/img/icons/firefox/firefox-general-16-16.png",
      ]);
    })
  );
});

self.addEventListener("fetch", (event) => {
  if (!event.request.url.includes("api")) {
    event.respondWith(
      caches
        .match(event.request)
        .then(function (response) {
          return response || fetch(event.request);
        })
        .catch(function () {
          return caches.match("/offline.html");
        })
    );
  }
});
