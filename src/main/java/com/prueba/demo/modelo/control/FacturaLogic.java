package com.prueba.demo.modelo.control;

import com.prueba.demo.dataaccess.dao.*;
import com.prueba.demo.exceptions.*;
import com.prueba.demo.modelo.*;
import com.prueba.demo.modelo.dto.FacturaDTO;
import com.prueba.demo.utilities.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
* @author Zathura Code Generator http://zathuracode.org
* www.zathuracode.org
*
*/
@Scope("singleton")
@Service("FacturaLogic")
public class FacturaLogic implements IFacturaLogic {
    private static final Logger log = LoggerFactory.getLogger(FacturaLogic.class);

    /**
     * DAO injected by Spring that manages Factura entities
     *
     */
    @Autowired
    private IFacturaDAO facturaDAO;

    /**
    * Logic injected by Spring that manages Reserva entities
    *
    */
    @Autowired
    IReservaLogic logicReserva1;

    @Transactional(readOnly = true)
    public List<Factura> getFactura() throws Exception {
        log.debug("finding all Factura instances");

        List<Factura> list = new ArrayList<Factura>();

        try {
            list = facturaDAO.findAll();
        } catch (Exception e) {
            log.error("finding all Factura failed", e);
            throw new ZMessManager().new GettingException(ZMessManager.ALL +
                "Factura");
        } finally {
        }

        return list;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveFactura(Factura entity) throws Exception {
        log.debug("saving Factura instance");

        try {
            if (entity.getReserva() == null) {
                throw new ZMessManager().new ForeignException("reserva");
            }

            if ((entity.getEstadoPago() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getEstadoPago(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "estadoPago");
            }

            if (entity.getIdFactura() == null) {
                throw new ZMessManager().new EmptyFieldException("idFactura");
            }

            if ((entity.getMetodoPago() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getMetodoPago(), 45) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "metodoPago");
            }

            if ((entity.getValorTotal() != null) &&
                    (Utilities.checkNumberAndCheckWithPrecisionAndScale("" +
                        entity.getValorTotal(), 12, 0) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "valorTotal");
            }

            if (entity.getReserva().getIdReserva() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "idReserva_Reserva");
            }

            if (getFactura(entity.getIdFactura()) != null) {
                throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
            }

            facturaDAO.save(entity);

            log.debug("save Factura successful");
        } catch (Exception e) {
            log.error("save Factura failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteFactura(Factura entity) throws Exception {
        log.debug("deleting Factura instance");

        if (entity == null) {
            throw new ZMessManager().new NullEntityExcepcion("Factura");
        }

        if (entity.getIdFactura() == null) {
            throw new ZMessManager().new EmptyFieldException("idFactura");
        }

        try {
            facturaDAO.delete(entity);

            log.debug("delete Factura successful");
        } catch (Exception e) {
            log.error("delete Factura failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateFactura(Factura entity) throws Exception {
        log.debug("updating Factura instance");

        try {
            if (entity == null) {
                throw new ZMessManager().new NullEntityExcepcion("Factura");
            }

            if (entity.getReserva() == null) {
                throw new ZMessManager().new ForeignException("reserva");
            }

            if ((entity.getEstadoPago() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getEstadoPago(), 255) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "estadoPago");
            }

            if (entity.getIdFactura() == null) {
                throw new ZMessManager().new EmptyFieldException("idFactura");
            }

            if ((entity.getMetodoPago() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getMetodoPago(), 45) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "metodoPago");
            }

            if ((entity.getValorTotal() != null) &&
                    (Utilities.checkNumberAndCheckWithPrecisionAndScale("" +
                        entity.getValorTotal(), 12, 0) == false)) {
                throw new ZMessManager().new NotValidFormatException(
                    "valorTotal");
            }

            if (entity.getReserva().getIdReserva() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "idReserva_Reserva");
            }

            facturaDAO.update(entity);

            log.debug("update Factura successful");
        } catch (Exception e) {
            log.error("update Factura failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = true)
    public List<FacturaDTO> getDataFactura() throws Exception {
        try {
            List<Factura> factura = facturaDAO.findAll();

            List<FacturaDTO> facturaDTO = new ArrayList<FacturaDTO>();

            for (Factura facturaTmp : factura) {
                FacturaDTO facturaDTO2 = new FacturaDTO();

                facturaDTO2.setIdFactura(facturaTmp.getIdFactura());
                facturaDTO2.setEstadoPago((facturaTmp.getEstadoPago() != null)
                    ? facturaTmp.getEstadoPago() : null);
                facturaDTO2.setMetodoPago((facturaTmp.getMetodoPago() != null)
                    ? facturaTmp.getMetodoPago() : null);
                facturaDTO2.setValorTotal((facturaTmp.getValorTotal() != null)
                    ? facturaTmp.getValorTotal() : null);
                facturaDTO2.setIdReserva_Reserva((facturaTmp.getReserva()
                                                            .getIdReserva() != null)
                    ? facturaTmp.getReserva().getIdReserva() : null);
                facturaDTO.add(facturaDTO2);
            }

            return facturaDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Factura getFactura(Integer idFactura) throws Exception {
        log.debug("getting Factura instance");

        Factura entity = null;

        try {
            entity = facturaDAO.findById(idFactura);
        } catch (Exception e) {
            log.error("get Factura failed", e);
            throw new ZMessManager().new FindingException("Factura");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public List<Factura> findPageFactura(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception {
        List<Factura> entity = null;

        try {
            entity = facturaDAO.findPage(sortColumnName, sortAscending,
                    startRow, maxResults);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Factura Count");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public Long findTotalNumberFactura() throws Exception {
        Long entity = null;

        try {
            entity = facturaDAO.count();
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Factura Count");
        } finally {
        }

        return entity;
    }

    /**
    *
    * @param varibles
    *            este arreglo debera tener:
    *
    * [0] = String variable = (String) varibles[i]; representa como se llama la
    * variable en el pojo
    *
    * [1] = Boolean booVariable = (Boolean) varibles[i + 1]; representa si el
    * valor necesita o no ''(comillas simples)usado para campos de tipo string
    *
    * [2] = Object value = varibles[i + 2]; representa el valor que se va a
    * buscar en la BD
    *
    * [3] = String comparator = (String) varibles[i + 3]; representa que tipo
    * de busqueda voy a hacer.., ejemplo: where nombre=william o where nombre<>william,
        * en este campo iria el tipo de comparador que quiero si es = o <>
            *
            * Se itera de 4 en 4..., entonces 4 registros del arreglo representan 1
            * busqueda en un campo, si se ponen mas pues el continuara buscando en lo
            * que se le ingresen en los otros 4
            *
            *
            * @param variablesBetween
            *
            * la diferencia son estas dos posiciones
            *
            * [0] = String variable = (String) varibles[j]; la variable ne la BD que va
            * a ser buscada en un rango
            *
            * [1] = Object value = varibles[j + 1]; valor 1 para buscar en un rango
            *
            * [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en un rango
            * ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria value2
                *
                * [3] = String comparator1 = (String) varibles[j + 3]; comparador 1
                * ejemplo: a comparator1 1 and a < 5
                    *
                    * [4] = String comparator2 = (String) varibles[j + 4]; comparador 2
                    * ejemplo: a comparador1>1  and a comparador2<5  (el original: a > 1 and a <
                            * 5) *
                            * @param variablesBetweenDates(en
                            *            este caso solo para mysql)
                            *  [0] = String variable = (String) varibles[k]; el nombre de la variable que hace referencia a
                            *            una fecha
                            *
                            * [1] = Object object1 = varibles[k + 2]; fecha 1 a comparar(deben ser
                            * dates)
                            *
                            * [2] = Object object2 = varibles[k + 3]; fecha 2 a comparar(deben ser
                            * dates)
                            *
                            * esto hace un between entre las dos fechas.
                            *
                            * @return lista con los objetos que se necesiten
                            * @throws Exception
                            */
    @Transactional(readOnly = true)
    public List<Factura> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception {
        List<Factura> list = new ArrayList<Factura>();
        String where = new String();
        String tempWhere = new String();

        if (variables != null) {
            for (int i = 0; i < variables.length; i++) {
                if ((variables[i] != null) && (variables[i + 1] != null) &&
                        (variables[i + 2] != null) &&
                        (variables[i + 3] != null)) {
                    String variable = (String) variables[i];
                    Boolean booVariable = (Boolean) variables[i + 1];
                    Object value = variables[i + 2];
                    String comparator = (String) variables[i + 3];

                    if (booVariable.booleanValue()) {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " \'" +
                            value + "\' )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " \'" + value + "\' )");
                    } else {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " " +
                            value + " )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " " + value + " )");
                    }
                }

                i = i + 3;
            }
        }

        if (variablesBetween != null) {
            for (int j = 0; j < variablesBetween.length; j++) {
                if ((variablesBetween[j] != null) &&
                        (variablesBetween[j + 1] != null) &&
                        (variablesBetween[j + 2] != null) &&
                        (variablesBetween[j + 3] != null) &&
                        (variablesBetween[j + 4] != null)) {
                    String variable = (String) variablesBetween[j];
                    Object value = variablesBetween[j + 1];
                    Object value2 = variablesBetween[j + 2];
                    String comparator1 = (String) variablesBetween[j + 3];
                    String comparator2 = (String) variablesBetween[j + 4];
                    tempWhere = (tempWhere.length() == 0)
                        ? ("(" + value + " " + comparator1 + " " + variable +
                        " and " + variable + " " + comparator2 + " " + value2 +
                        " )")
                        : (tempWhere + " AND (" + value + " " + comparator1 +
                        " " + variable + " and " + variable + " " +
                        comparator2 + " " + value2 + " )");
                }

                j = j + 4;
            }
        }

        if (variablesBetweenDates != null) {
            for (int k = 0; k < variablesBetweenDates.length; k++) {
                if ((variablesBetweenDates[k] != null) &&
                        (variablesBetweenDates[k + 1] != null) &&
                        (variablesBetweenDates[k + 2] != null)) {
                    String variable = (String) variablesBetweenDates[k];
                    Object object1 = variablesBetweenDates[k + 1];
                    Object object2 = variablesBetweenDates[k + 2];
                    String value = null;
                    String value2 = null;

                    try {
                        Date date1 = (Date) object1;
                        Date date2 = (Date) object2;
                        value = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date1);
                        value2 = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date2);
                    } catch (Exception e) {
                        list = null;
                        throw e;
                    }

                    tempWhere = (tempWhere.length() == 0)
                        ? ("(model." + variable + " between \'" + value +
                        "\' and \'" + value2 + "\')")
                        : (tempWhere + " AND (model." + variable +
                        " between \'" + value + "\' and \'" + value2 + "\')");
                }

                k = k + 2;
            }
        }

        if (tempWhere.length() == 0) {
            where = null;
        } else {
            where = "(" + tempWhere + ")";
        }

        try {
            list = facturaDAO.findByCriteria(where);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
        }

        return list;
    }
}
