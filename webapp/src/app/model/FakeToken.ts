import { UnsecuredJWT } from 'jose';
import { Token } from './Token';

export interface FakeToken extends Token {}

export const newFakeToken = (durationInSeconds: number): FakeToken => {
  return {
    access_token: new UnsecuredJWT({})
      .setIssuedAt(Math.floor(new Date().getTime() / 1000))
      .setExpirationTime(
        Math.floor(new Date().getTime() / 1000) + durationInSeconds
      )
      .encode(),
    expires_in: durationInSeconds,
  };
};
