package controllerservice.props;

public interface PropsControllerService {
	
	/**
	 * Contact the <code><b>PropsControllerService</code></b> to getCoin.
	 *  
	 * @see ChoosePropsPanel
	 * @param userName
	 * @return 
	 */
	public int getCoin(String userName);
	
	/**
	 * Contact the <code><b>PropsControllerService</code></b> to updateCoin.
	 *  
	 * @see ChoosePropsPanel
	 * @param isUp,isPrompt,isChain,isBack,isShark,userName
	 * @return 
	 */
	public int updateCoin(boolean isUp, boolean isPrompt, boolean isChain, boolean isBack, boolean isShark,
			String userName);
	
	/**
	 * Contact the <code><b>PropsControllerService</code></b> to getLocalCoin.
	 *  
	 * @see ChoosePropsPanel
	 * @param 
	 * @return 
	 */
	 public int getLocalCoin();
	    
	/**
	 * Contact the <code><b>PropsControllerService</code></b> to updateLocalCoin.
	 *  
	 * @see ChoosePropsPanel
	 * @param isUp,isPrompt,isChain,isBack,isShark,userName
	 * @return 
	 */		 
	 public int updateLocalCoin(boolean isUp, boolean isPrompt, boolean isChain, boolean isBack, boolean isShark);
}
