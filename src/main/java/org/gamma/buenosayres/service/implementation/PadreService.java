package org.gamma.buenosayres.service.implementation;

import java.util.List;
import java.util.UUID;

import org.gamma.buenosayres.dao.interfaces.PadreDAO;
import org.gamma.buenosayres.model.Padre;
import org.gamma.buenosayres.model.TipoPersona;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class PadreService {

     private PadreDAO padreDAO;

     public PadreService(PadreDAO padreDAO) {
          this.padreDAO = padreDAO;
     }

     public List<Padre> read() {
          return padreDAO.findAll();
     }

     public Padre create(Padre padre) throws ServiceException {
          if (padreDAO.findPadreByPersona_Dni(padre.getPersona().getDni()).isPresent()) {
               throw new ServiceException("Padre ya registrado", 400);
          }
          padre.getPersona().setTipo(TipoPersona.PADRE);
          padreDAO.save(padre);
          return padre;
     }

     public Padre read(UUID idPadre) throws ServiceException {
          return padreDAO.findById(idPadre).orElseThrow(() -> new ServiceException("No encontrado", 404));
     }

     public Padre update(Padre padre) {
          return padreDAO.save(padre);
     }
     //TODO: Direcciones de los alumnos
}
