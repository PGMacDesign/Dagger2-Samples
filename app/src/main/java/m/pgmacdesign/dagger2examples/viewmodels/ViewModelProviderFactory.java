package m.pgmacdesign.dagger2examples.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelProviderFactory implements ViewModelProvider.Factory {

	private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
	
	@Inject
	public ViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators){
		this.creators = creators;
	}
	
	public <T extends ViewModel> T create(Class<T> modelClass){
		Provider<?  extends ViewModel> creator = this.creators.get(modelClass);
		//Means the view model has not been created
		if(creator == null){
			//Loop through allowable keys (Allowed classes with the @ViewModelKey annotation)
			if (!MiscUtilities.isMapNullOrEmpty(this.creators)) {
				for(Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : this.creators.entrySet()){
					//If it is allowed, set the Provider (ViewModel)
					if(modelClass.isAssignableFrom(entry.getKey())){
						creator = entry.getValue();
					}
				}
			}
		}
		
		//Not an allowed Key
		if(creator == null){
			throw new IllegalArgumentException("unknown model class " + modelClass);
		}
		
		//Return the provider
		try {
			return (T) creator.get();
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
