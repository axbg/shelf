const getIdTokenInteractively = function (interactive) {
  return new Promise((resolve, reject) => {
    const manifest = chrome.runtime.getManifest();

    const clientId = encodeURIComponent(manifest.oauth2.client_id);
    const scopes = encodeURIComponent(manifest.oauth2.scopes.join(' '));
    const redirectUri = encodeURIComponent('https://' + chrome.runtime.id + '.chromiumapp.org');

    const url = 'https://accounts.google.com/o/oauth2/v2/auth' +
      '?client_id=' + clientId +
      '&response_type=id_token' +
      '&access_type=offline' +
      '&redirect_uri=' + redirectUri +
      '&scope=' + scopes +
      '&nonce=somenoncehere' +
      '&prompt=';

    chrome.identity.launchWebAuthFlow(
      {
        'url': url,
        'interactive': interactive
      },
      function (redirectedTo) {
        if (chrome.runtime.lastError) {
          resolve(null);
        }
        else {
          const response = redirectedTo.split('#', 2)[1];
          resolve(response);
        }
      }
    );
  });
};