package cc.coopersoft.framework.controller;

import cc.coopersoft.framework.PageResultData;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.services.BusinessDefineService;
import cc.coopersoft.framework.services.BusinessInstanceService;
import cc.coopersoft.framework.tools.DataHelper;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@RequestScoped
public class BusinessListController extends cc.coopersoft.framework.EntityListBaseController<BusinessInstance> {

    private static final int PAGE_SIZE = 10;

    @Inject
    private BusinessInstanceService<? extends BusinessInstance> businessInstanceService;

    @Inject
    private BusinessDefineService businessDefineService;

    @Inject @Param(name = "categoryId")
    private String categoryId;

    @Inject @Param(name = "defineId")
    private String defineId;

    private List<KeyAndCount> businessDefines;

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<KeyAndCount> getBusinessDefines() {
        //TODO count to many change to category
        return businessDefines;
    }

    private List<String> getLimitDefineIds(){
        List<String> result = new ArrayList<>();
        if (DataHelper.empty(categoryId)){
            if (!DataHelper.empty(defineId)){
                result.add(defineId);
            }
        }else{
            //TODO read all define id from category;
        }
        return result;
    }


    @Override
    protected void fillResult(){
        resultCount = businessInstanceService.searchCount(getCondition(),getLimitDefineIds());

        fillResult(new ArrayList<>(businessInstanceService.search(getCondition(),getLimitDefineIds(),getOffset(),PAGE_SIZE)),getOffset(),resultCount,PAGE_SIZE);

        businessDefines = businessInstanceService.searchDefineCount(getCondition(),getLimitDefineIds());
        for(KeyAndCount keyAndCount: businessDefines){
            keyAndCount.setPri(9999);
            for(BusinessDefineEntity e: businessDefineService.findAll()){
                if(keyAndCount.getKey().equals(e.getId())) {
                    keyAndCount.setName(e.getName());
                    keyAndCount.setPri(e.getPriority());
                    break;
                }
            }
        }
        Collections.sort(businessDefines);
    }

}
