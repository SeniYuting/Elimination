package controllerservice.user;

/**
 * <code><b>RegisterControllerService</b></code> contains components about handling register issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface RegisterControllerService {
	/**
	 * Contact the <code><b>RegisterControllerService</code></b> to register.
	 * @see LoginFrame
	 * @param userName,password
	 * @return
	 */
	public abstract String register(String userName,String password);
}
