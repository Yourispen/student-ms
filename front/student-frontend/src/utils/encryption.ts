import Cryptr from "cryptr";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function encrypt(text:any) {
  const secretKey = process.env.NEXTAUTH_SECRET;
  const cryptr = new Cryptr(secretKey!);

  const encryptedString = cryptr.encrypt(text);
  return encryptedString; 
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function decrypt(encryptedString:any) {
    const secretKey = process.env.NEXTAUTH_SECRET;
    const cryptr = new Cryptr(secretKey!);
  
    const text = cryptr.decrypt(encryptedString);
    return text;
  }