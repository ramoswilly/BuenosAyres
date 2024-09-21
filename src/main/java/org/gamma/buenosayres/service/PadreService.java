package org.gamma.buenosayres.service;

import java.util.List;
import java.util.UUID;

import org.gamma.buenosayres.repository.PadreDAO;
import org.gamma.buenosayres.model.Padre;
import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class PadreService {

     private final PadreDAO padreDAO;
     private final PersonaService personaService;
     private final UserService userService;
     public PadreService(PadreDAO padreDAO, PersonaService personaService, UserService userService) {
          this.padreDAO = padreDAO;
          this.personaService = personaService;
          this.userService = userService;
     }

     public List<Padre> read() {
          return padreDAO.findAll();
     }

     public Padre create(Padre padre) throws ServiceException {
          if (padreDAO.findPadreByPersona_Dni(padre.getPersona().getDni()).isPresent()) {
               throw new ServiceException("Padre ya registrado", 400);
          }
          // Crear persona
          padre.setPersona(personaService.create(padre.getPersona()));
          // Crear usuario
          Usuario usuario = userService.create(padre.getPersona().getId());
          padre.getPersona().setUsuario(usuario);
          // Agregar rol..
          userService.giveRole(usuario, "ROLE_PADRE");
          return padreDAO.save(padre);
     }

     public Padre read(UUID idPadre) throws ServiceException {
          return padreDAO.findById(idPadre).orElseThrow(() -> new ServiceException("No encontrado", 404));
     }

     public Padre update(Padre padre) {
          return padreDAO.save(padre);
     }
     //TODO: Direcciones de los alumnos
}
