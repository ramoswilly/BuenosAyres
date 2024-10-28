package org.gamma.buenosayres.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.gamma.buenosayres.repository.PadreDAO;
import org.gamma.buenosayres.model.Padre;
import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.repository.UsuarioDAO;
import org.springframework.stereotype.Component;

@Component
public class PadreService {

     private final PadreDAO padreDAO;
     private final PersonaService personaService;
     private final UserService userService;
     private final UsuarioDAO usuarioDAO;
     public PadreService(PadreDAO padreDAO, PersonaService personaService, UserService userService, UsuarioDAO usuarioDAO) {
          this.padreDAO = padreDAO;
          this.personaService = personaService;
          this.userService = userService;
          this.usuarioDAO = usuarioDAO;
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
          // Habilitado
          padre.setHabilitado(true);
          // Agregar rol..
          userService.giveRole(usuario, "ROLE_PADRE");
          return padreDAO.save(padre);
     }

     public Padre read(UUID idPadre) throws ServiceException
     {
          return padreDAO.findById(idPadre).orElseThrow(() -> new ServiceException("No encontrado", 404));
     }

     public Padre update(Padre padre) throws ServiceException
     {
          if (padre.getId() == null) throw new ServiceException("Id de padre no puede ser null", 400);
          Optional<Padre> padreSync = padreDAO.findById(padre.getId());
          if (padreSync.isEmpty()) throw new ServiceException("Padre inexistente", 404);
          if (padre.isHabilitado() != padreSync.get().isHabilitado()) {
               padreSync.get().setHabilitado(padre.isHabilitado());
               userService.enable(padreSync.get().getPersona().getUsuario(), padre.isHabilitado());
          }
          if (padre.getPersona() != null) {
               if (padre.getPersona().getDni() != null) {
                    Optional<Padre> padreByDni = padreDAO.findPadreByPersona_Dni(padre.getPersona().getDni());
                    if (padreByDni.isPresent() && !padreByDni.get().getId().equals(padre.getId())) {
                         throw new ServiceException("No se puede actualizar DNI, corresponde a otro padre", 400);
                    }
                    padreSync.get().getPersona().getUsuario().setUsername(padre.getPersona().getDni());
                    padreSync.get().getPersona().getUsuario().setPassword(padre.getPersona().getDni());
                    usuarioDAO.save(padreSync.get().getPersona().getUsuario());
                    padreSync.get().getPersona().setDni(padre.getPersona().getDni());
               }
               if (padre.getPersona().getNombre() != null) {
                    padreSync.get().getPersona().setNombre(padre.getPersona().getNombre());
               }
               if (padre.getPersona().getApellido() != null) {
                    padreSync.get().getPersona().setApellido(padre.getPersona().getApellido());
               }
               if (padre.getPersona().getDireccion() != null) {
                    padreSync.get().getPersona().setDireccion(padre.getPersona().getDireccion());
               }
          }
          if (padre.getEmail() != null) {
               padreSync.get().setEmail(padre.getEmail());
          }
          if (padre.getCuit() != null) {
               padreSync.get().setCuit(padre.getCuit());
          }
          if (padre.getTelefono() != null) {
               padreSync.get().setTelefono(padre.getTelefono());
          }
          if (padre.isResponsableFacturacion() != padreSync.get().isResponsableFacturacion()) {
               padreSync.get().setResponsableFacturacion(padre.isResponsableFacturacion());
          }
          return padreDAO.save(padreSync.get());
     }
}
