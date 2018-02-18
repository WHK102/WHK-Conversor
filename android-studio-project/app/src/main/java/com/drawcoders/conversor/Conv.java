package com.drawcoders.conversor;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by whk on 26-04-15.
 */
public class Conv {

    /* Public functions ============================================ */
    public static String base64_encode(String string) {
        /*
        int splitLinesAt = 76;
        String base64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "+/";
        String encoded = "";
        byte[] stringArray;

        try{
            stringArray = string.getBytes("UTF-8");
        }catch (Exception ignored){
            stringArray = string.getBytes();
        }

        int paddingCount = (3 - (stringArray.length % 3)) % 3;

        byte[] padded = new byte[stringArray.length + paddingCount];
        System.arraycopy(stringArray, 0, padded, 0, stringArray.length);
        stringArray = padded;

        for(int i = 0; i < stringArray.length; i += 3){
            int j = ((stringArray[i] & 0xff) << 16) + ((stringArray[i + 1] & 0xff) << 8) + (stringArray[i + 2] & 0xff);
            encoded = encoded + base64code.charAt((j >> 18) & 0x3f) + base64code.charAt((j >> 12) & 0x3f) + base64code.charAt((j >> 6) & 0x3f) + base64code.charAt(j & 0x3f);
        }

        String lines = "";
        String str = encoded.substring(0, encoded.length() - paddingCount) + "==".substring(0, paddingCount);
        for(int i = 0; i < str.length(); i += splitLinesAt){
            lines += str.substring(i, Math.min(str.length(), i + splitLinesAt));
            lines += "\r\n";
        }
        return lines;
        */
        try {
            byte[] data = string.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT).trim();
        } catch (Exception e) {
            return "Unsupported encoding Ascii to UTF-8";
        }
    }

    public static String base64ToAscii(String buffer){
        buffer = buffer.trim();
        try {
            byte[] data = Base64.decode(buffer, Base64.DEFAULT);
            return (new String(data, "UTF-8")).trim();
        } catch (Exception e) {
            return "The Base64 hash is corrupt";
        }
    }

    public static String MD5(String str){
        return digestToStr(str, "md5");
    }

    public static String SHA1(String str){
        return digestToStr(str, "sha-1");
    }

    public static String SHA256(String str){
        return digestToStr(str, "sha-256");
    }

    public static String SHA512(String str){
        return digestToStr(str, "sha-512");
    }

    public static String RC4(String str, String key){
        return toRC4(str, key);
    }

    public static String strDec(String str){
        return toUnicodePrefix(str, "", " ");
    }

    public static String decToStr(String buffer){
        buffer = buffer
                .replace(",", " ")
                .replace(".", " ")
                .replace("/", " ")
                .replace("-", " ")
                .replace("\n", " ")
                .replace("\r", " ")
                .replace("\t", " ")
                .replaceAll("[^0-9 ]", "")
                .replaceAll("\\s+", " ");

        String str = "";
        String[] parts = buffer.split(" ");
        buffer = null; // Empty memory

        for(String part : parts){
            try {
                int primitive_part = stringToInteger(part);
                char character = (char) primitive_part;
                str += character;
            }catch(Exception e){
                str += "?";
            }
        }

        return str;
    }

    public static String strToOctal(String str){
        return toOctal(str, "", " ");
    }

    public static String strHex(String str){
        return strToHexPrefix(str, "\\x", "");
    }

    public static String urlencode(String str){
        return strToHexPrefix(str, "%", "");
    }

    public static String strToHtmlentities(String str){
        return toUnicodePrefix(str, "&#", "");
    }

    public static String strToSqliDword(String str){
        return "function(0x" + strToHexPrefix(str, "", "") + ")";
    }

    public static String strToBinary(String str){
        return toBinary(str, "", " ");
    }

    public static String checkMD5Sum(String filename){
        return checkSum(filename, "md5");
    }

    public static String checkSHA1Sum(String filename){
        return checkSum(filename, "sha-1");
    }

    public static String matrixCode(String str){
        return "<html><head><style type='text/css'>body{background: #000;overflow: hidden;line-height: 15px;font-size: 14px;} div.cadena{position: absolute;color: #0f0;text-align: center;width: 10px;overflow: visible;font-family: 'Courier New';}</style><script type='text/javascript'>var NUMERO_CADENAS=70,TAMANO_CADENA=25,VELOCIDAD_MAXIMA=100,cadenas=Array(NUMERO_CADENAS);window.onload=function(){for(var a=0;a<NUMERO_CADENAS;a++){var b=document.createElement('div');b.className='cadena';b.innerHTML=getTexto();cadenas[a]=b;document.body.appendChild(cadenas[a]);prepararCadena(b)}animar()};function animar(){for(var a,b=0;b<cadenas.length;b++)a=cadenas[b],a.style.top=a.offsetTop+a.velocidad+'px',a.offsetTop>window.innerHeight&&prepararCadena(a);setTimeout('animar();',50)} function prepararCadena(a){a.velocidad=Math.round(Math.random()*VELOCIDAD_MAXIMA)+1;a.style.color='rgb(0,'+Math.round(255*Math.random())+',0)';a.style.left=Math.round(Math.random()*document.body.offsetWidth)+'px';a.style.top=-a.offsetHeight+'px'}function generarCadenaAleatoria(){for(var a='',b=0,c=0;c<TAMANO_CADENA;c++)b=Math.round(27*Math.random())+65,a+=String.fromCharCode(b)+' ';return a} function getTexto(){return '" +
                strToHexPrefix(str, "\\x", " ") +
                "';}</script></head><body></body></html>";
    }

    /* Private functions ============================================= */
    private static Integer stringToInteger(String string){
        try {
            if (string != null) {
                return Integer.parseInt(string);
            } else {
                return null;
            }
        }catch(Exception e){
            return null;
        }
    }

    private static String checkSum(String filename, String algorithm){
        try{
            byte[] bytes = null;

			/* Read binary file */
            File fileHandle = new File(filename);
            byte[] buffer = new byte[(int)fileHandle.length()];
            FileInputStream fis = new FileInputStream(fileHandle);
            fis.read(buffer);

			/* Convert */
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
            messageDigest.reset();
            messageDigest.update(buffer);
            bytes = messageDigest.digest();
            return byteToHexadecimal(bytes);

        }catch(FileNotFoundException e){
            return "Error: " + e.getMessage().toString();
        }catch (IOException e){
            return "Error: " + e.getMessage().toString();
        } catch (NoSuchAlgorithmException e) {
            return "Error: " + e.getMessage().toString();
        }
    }

    private static String toRC4(String buff, String key){
        if(key.isEmpty()){
            return "Invalid key length";
        }

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            return "0x" + byteToHexadecimal(cipher.doFinal(buff.getBytes()));

        } catch (NoSuchAlgorithmException e) {
            return "Error: " + e.getMessage().toString();
        } catch (NoSuchPaddingException e) {
            return "Error: " + e.getMessage().toString();
        } catch (InvalidKeyException e) {
            return "Error: " + e.getMessage().toString();
        } catch (IllegalBlockSizeException e) {
            return "Error: " + e.getMessage().toString();
        } catch (BadPaddingException e) {
            return "Error: " + e.getMessage().toString();
        }
    }

    private static String byteToHexadecimal(byte[] bt) {
        String hash = "";
        for (byte aux : bt) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1)
                hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    private static String digestToStr(String str, String algorithm){
        try{
            byte[] bytes = null;
            byte[] buffer = str.getBytes();
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
            messageDigest.reset();
            messageDigest.update(buffer);
            bytes = messageDigest.digest();
            return byteToHexadecimal(bytes);
        }catch (NoSuchAlgorithmException e){
            return "Error: " + e.getMessage().toString();
        }
    }

    public static String strToHexPrefix(String buff, String prefix, String sufix){
        byte[] bt = buff.getBytes();
        String hash = "";
        for (byte aux : bt) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1)
                hash += "0";
            hash += prefix + Integer.toHexString(b) + sufix;
        }
        return hash;
    }

    public static String hexToStr(String buffer){
        buffer = buffer.trim().toLowerCase();

        if(buffer.startsWith("0x"))
            buffer = buffer.substring(2);

        buffer = buffer.replaceAll("[^0-9a-f]", "");

        StringBuilder sb = new StringBuilder();
        char[] hexData = buffer.toCharArray();
        for (int count = 0; count < hexData.length - 1; count += 2) {
            int firstDigit = Character.digit(hexData[count], 16);
            int lastDigit = Character.digit(hexData[count + 1], 16);
            int decimal = firstDigit * 16 + lastDigit;
            sb.append((char)decimal);
        }
        return sb.toString();
    }

    private static String toUnicodePrefix(String str, String prefix, String sufix){
        StringBuilder sb = new StringBuilder();
        byte[] writeBuf  = str.getBytes();
        for (int i =0; i < writeBuf.length; ++i)
            sb.append(prefix + writeBuf[i] + sufix);
        return sb.toString();
    }

    private static String toOctal(String str, String prefix, String sufix){
        StringBuilder sb = new StringBuilder();
        byte[] writeBuf  = str.getBytes();

        for (int i =0; i < writeBuf.length; ++i)
            sb.append(prefix + Integer.toOctalString(writeBuf[i]) + sufix);

        return sb.toString();
    }

    public static String octalToStr(String buffer){
        buffer = buffer
                .replace(",", " ")
                .replace(".", " ")
                .replace("/", " ")
                .replace("-", " ")
                .replace("\n", " ")
                .replace("\r", " ")
                .replace("\t", " ")
                .replaceAll("[^0-9 ]", "")
                .replaceAll("\\s+", " ");

        String str = "";
        String[] parts = buffer.split(" ");
        buffer = null; // Empty memory

        for(String part : parts){
            try {
                int primitive_part = stringToInteger(part);

                int ascii = Integer.parseInt(Integer.toString(primitive_part), 8);
                str += "" + (char)ascii;

            }catch(Exception e){
                str += "?";
            }
        }

        return str;
    }

    public static String toBinary(String str, String prefix, String sufix){
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes){
            int val = b;
            binary.append(prefix);
            for (int i = 0; i < 8; i++){
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(sufix);
        }
        return binary.toString();
    }

    public static String binaryToAscii(String buffer){
        try {
            buffer = buffer.trim().replaceAll("[^0-1]", "");

            String result = "";
            char nextChar;
            for (int i = 0; i <= buffer.length() - 8; i += 8) { //this is a little tricky.  we want [0, 7], [9, 16], etc
                nextChar = (char) Integer.parseInt(buffer.substring(i, i + 8), 2);
                result += nextChar;
            }
            return result;
        }catch(Exception e){
            return "Incorrect format binary";
        }
    }
}
