package main.asw.user;

import main.asw.encryption.EncryptionUtils;


/**
 * Created by nicolas on 3/02/17.
 *
 * @author nicolas
 * @author MIGUEL
 */
public class User {
	private String name;
	private GeoCords location;
	private String email;
	private String identifier;
	private int kind;
	private String kindCode;
	
    private String password;
    private String unencryptedPass;

    public User(String name, GeoCords location, String email, String identifier, int kind, String kindCode) {
		super();
		this.name = name;
		this.location = location;
		this.setEmail(email);
		this.setNif(identifier);
		this.kind = kind;
		this.unencryptedPass = EncryptionUtils.getInstance().generatePassword();
        this.password = EncryptionUtils.getInstance().encryptPassword(unencryptedPass);
        this.kindCode = kindCode;
	}

    @Override
	public String toString() {
		return "User [name=" + name + ", location=" + location + ", email=" + email + ", identifier=" + identifier
				+ ", kind=" + kind + "]";
	}

	public String getUnencryptedPass() {
        return unencryptedPass;
    }

    public String getName() {
		return name;
	}   

	public String getLocation() {
		return location.toString();
	}

	public String getEmail() {
        return email;
    }	

    public String getIdentifier() {
		return identifier;
	}

	public int getKind() {
		return kind;
	}

	public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        if (validateEmail(email))
            this.email = email;
        else
            throw new IllegalArgumentException("The email is not well formed");
    }

    private boolean validateEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void setNif(String nif) {
        if (validateNif(nif))
            this.identifier = nif;
        else
            throw new IllegalArgumentException("The nif is not well formed");
    }

    private boolean validateNif(String nif) {
        Boolean res = true;
        if (nif.length() == 9) {
            int dni = Integer.parseInt(nif.substring(0, 8));
            String juegoCaracteres = "TRWAGMYFPDXBNJZSQVHLCKE";
            int modulo = dni % 23;
            char letra = juegoCaracteres.charAt(modulo);
            if (nif.charAt(8) != letra)
                res = false;
        } else {
            res = false;
        }
        return res;
    }

	public String getKindCode() {
		return kindCode;
	}
}
