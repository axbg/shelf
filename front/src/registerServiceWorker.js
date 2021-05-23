/* eslint-disable no-console */
import { register } from "register-service-worker";

if (process.env.NODE_ENV === "production") {
  register('service-worker.js', {
    registered() {
      console.log("Service worker has been registered.");
    }
  });
}
