const fs = require("fs");
const manifestPath = "./dist/manifest.json";
const shareTarget = require("./share-target.json");

const manifest = JSON.parse(
  fs.readFileSync(manifestPath, { encoding: "utf-8" })
);
manifest["share_target"] = shareTarget["share_target"];
fs.writeFileSync(manifestPath, JSON.stringify(manifest));

console.log("----- Prepared manifest for deployment -----");
