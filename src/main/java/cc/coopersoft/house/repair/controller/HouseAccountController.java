package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.EntityController;
import cc.coopersoft.framework.services.EntityService;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.services.HouseAccountService;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class HouseAccountController extends EntityController<HouseAccountEntity,String> {

    @Override
    @Inject @Param(name = "accountId")
    public String getId(){
        return super.getId();
    }

    @Inject
    private HouseAccountService houseAccountService;

    @Override
    protected EntityService<HouseAccountEntity, String> getEntityService() {
        return houseAccountService;
    }



}
