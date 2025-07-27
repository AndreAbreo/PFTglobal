"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";

interface TipoIntervencion {
  id: number;
  nombreTipo: string;
  estado: string;
}

const ListarTiposIntervenciones: React.FC = () => {
  const [tiposIntervenciones, setTiposIntervenciones] = useState<TipoIntervencion[]>([]);
  const [filteredTiposIntervenciones, setFilteredTiposIntervenciones] = useState<TipoIntervencion[]>([]);
  const [filters, setFilters] = useState({ nombre: "", estado: "ACTIVO" });
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    setLoading(true);
    try {
      const data = await fetcher<TipoIntervencion[]>("/tipoIntervenciones/listar", { method: "GET" });
      setTiposIntervenciones(data);
      applyFilters(data, filters);
    } catch (err: any) {
      setError(err.message);
    }
    setLoading(false);
  };

  const applyFilters = (data: TipoIntervencion[], filters: { nombre: string; estado: string }) => {
    const filtered = data.filter((item) => {
      const matchNombre = item.nombreTipo.toLowerCase().includes(filters.nombre.toLowerCase());
      const matchEstado = filters.estado ? item.estado === filters.estado : true;
      return matchNombre && matchEstado;
    });
    setFilteredTiposIntervenciones(filtered);
  };

  const handleClearFilters = () => {
    const clearedFilters = { nombre: "", estado: "" };
    setFilters(clearedFilters);
    applyFilters(tiposIntervenciones, clearedFilters);
  };

  const handleInactivar = async (id: number) => {
    if (!confirm("¿Está seguro que desea inactivar este tipo de intervención?")) {
      return;
    }
    try {
      await fetcher(`/tipoIntervenciones/inactivar?id=${id}`, { method: "DELETE" });
      await handleSearch();
      alert("Tipo de intervención desactivado correctamente");
    } catch (err: any) {
      alert("Error al inactivar: " + err.message);
    }
  };

  useEffect(() => {
    handleSearch();
  }, []);

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="px-4 py-6 md:px-6 xl:px-7.5 flex justify-between items-center">
        <h4 className="text-xl font-semibold text-black dark:text-white">Tipos de Intervenciones</h4>
        <button
          onClick={() => window.location.href = '/tipoIntervenciones/crear'}
          className="inline-flex items-center justify-center rounded-md bg-primary px-4 py-2 text-center font-medium text-white hover:bg-opacity-90"
        >
          Crear Nuevo Tipo
        </button>
      </div>

      <div className="px-4 md:px-6 xl:px-7.5 mb-4">
        <h2 className="text-lg font-semibold mb-2">Filtros</h2>
        <div className="flex flex-wrap gap-4 items-end">
          <div className="flex flex-col">
            <label htmlFor="nombre" className="text-sm font-medium mb-1">Nombre:</label>
            <input
              type="text"
              id="nombre"
              value={filters.nombre}
              onChange={(e) => setFilters({ ...filters, nombre: e.target.value })}
              className="w-56 rounded border border-gray-300 p-2 bg-white text-black"
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="estado" className="text-sm font-medium mb-1">Estado:</label>
            <select
              id="estado"
              value={filters.estado}
              onChange={(e) => setFilters({ ...filters, estado: e.target.value })}
              className="w-36 rounded border border-gray-300 p-2 bg-white text-black"
            >
              <option value="">Todos</option>
              <option value="ACTIVO">Activos</option>
              <option value="INACTIVO">Eliminados</option>
            </select>
          </div>
          <div className="flex gap-2 mt-1">
            <button onClick={() => applyFilters(tiposIntervenciones, filters)} className="px-4 py-2 bg-blue-600 text-white rounded">Buscar</button>
            <button onClick={handleClearFilters} className="px-4 py-2 bg-gray-300 text-black rounded">Borrar Filtros</button>
          </div>
        </div>
      </div>

      {error && (
        <div className="mx-4 mb-4 p-4 bg-red-100 text-red-700 rounded-lg">Error: {error}</div>
      )}

      <div className="p-4 md:p-6 xl:p-7.5">
        {loading ? (
          <div className="flex justify-center items-center h-32">
            <div className="text-gray-600">Cargando...</div>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full table-auto">
              <thead>
                <tr className="bg-gray-2 text-left dark:bg-meta-4">
                  <th className="min-w-[80px] px-4 py-4 font-medium text-black dark:text-white">ID</th>
                  <th className="min-w-[220px] px-4 py-4 font-medium text-black dark:text-white">Nombre del Tipo</th>
                  <th className="min-w-[120px] px-4 py-4 font-medium text-black dark:text-white">Estado</th>
                  <th className="min-w-[120px] px-4 py-4 font-medium text-black dark:text-white">Acciones</th>
                </tr>
              </thead>
              <tbody>
                {filteredTiposIntervenciones.map((tipo, key) => (
                  <tr key={key}>
                    <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                      <p className="text-black dark:text-white">{tipo.id}</p>
                    </td>
                    <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                      <p className="text-black dark:text-white">{tipo.nombreTipo}</p>
                    </td>
                    <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                      <p className={`inline-flex rounded-full px-3 py-1 text-sm font-medium ${
                        tipo.estado === "ACTIVO"
                          ? "bg-success bg-opacity-10 text-success"
                          : "bg-danger bg-opacity-10 text-danger"
                      }`}>
                        {tipo.estado}
                      </p>
                    </td>
                    <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                      <div className="flex items-center space-x-3.5">
                        {tipo.estado === "ACTIVO" && (
                          <button
                            onClick={() => handleInactivar(tipo.id)}
                            className="inline-flex items-center justify-center rounded-md bg-red-600 px-3 py-1 text-center text-sm font-medium text-white hover:bg-opacity-90"
                            title="Inactivar tipo de intervención"
                          >
                            Inactivar
                          </button>
                        )}
                        {tipo.estado === "INACTIVO" && (
                          <span className="text-sm text-gray-500">Ya inactivo</span>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default ListarTiposIntervenciones;
