"use client";
import React, { useState, useEffect } from "react";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";
import fetcher from "@/components/Helpers/Fetcher";

interface Institucion {
  id: number;
  nombre: string;
}

interface Ubicacion {
  id: number;
  nombre: string;
  sector: string;
  piso: number | null;
  numero: number | null;
  cama: number | null;
  estado: string;
  idInstitucion?: Institucion | null;
}

const ListarUbicaciones: React.FC = () => {
  const [ubicaciones, setUbicaciones] = useState<Ubicacion[]>([]);
  const [filteredUbicaciones, setFilteredUbicaciones] = useState<Ubicacion[]>([]);
  const [filters, setFilters] = useState({ nombre: "", estado: "ACTIVO", sector: "", institucion: "" });
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchUbicaciones();
  }, []);

  const fetchUbicaciones = async () => {
    setLoading(true);
    try {
      const data = await fetcher<Ubicacion[]>("/ubicaciones/listar", { method: "GET" });
      setUbicaciones(data);
      setFilteredUbicaciones(data);
    } catch (err: any) {
      setError(err.message);
    }
    setLoading(false);
  };

  const applyFilters = (data: Ubicacion[], filters: { nombre: string; estado: string; sector: string; institucion: string }) => {
    return data.filter((item) => {
      const matchNombre = item.nombre.toLowerCase().includes(filters.nombre.toLowerCase());
      const matchEstado = filters.estado ? item.estado === filters.estado : true;
      const matchSector = item.sector.toLowerCase().includes(filters.sector.toLowerCase());
      const matchInstitucion = item.idInstitucion?.nombre.toLowerCase().includes(filters.institucion.toLowerCase()) ?? false;
      return matchNombre && matchEstado && matchSector && matchInstitucion;
    });
  };

  const handleSearch = () => {
    setFilteredUbicaciones(applyFilters(ubicaciones, filters));
  };

  const handleClearFilters = () => {
    const clearedFilters = { nombre: "", estado: "", sector: "", institucion: "" };
    setFilters(clearedFilters);
    setFilteredUbicaciones(applyFilters(ubicaciones, clearedFilters));
  };

  const columns: Column<Ubicacion>[] = [
    { header: "Nombre", accessor: "nombre", type: "text", filterable: true },
    { header: "Sector", accessor: "sector", type: "text", filterable: true },
    { header: "Piso", accessor: (row) => row.piso ?? "", type: "number", filterable: false },
    { header: "Número", accessor: (row) => row.numero ?? "", type: "number", filterable: false },
    { header: "Cama", accessor: (row) => row.cama ?? "", type: "number", filterable: false },
    { header: "Institución", accessor: (row) => row.idInstitucion?.nombre || "-", type: "text", filterable: false },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="px-4 py-6 md:px-6 xl:px-7.5">
        <h2 className="text-xl font-bold mb-4 text-black dark:text-white">Filtros</h2>
        <div className="flex flex-wrap gap-4 mb-4">
          <div className="flex flex-col">
            <label htmlFor="nombre" className="text-sm font-medium mb-1 text-white">Nombre:</label>
            <input
              type="text"
              id="nombre"
              value={filters.nombre}
              onChange={(e) => setFilters({ ...filters, nombre: e.target.value })}
              className="w-56 rounded border border-gray-300 p-2 bg-white text-black"
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="sector" className="text-sm font-medium mb-1 text-white">Sector:</label>
            <input
              type="text"
              id="sector"
              value={filters.sector}
              onChange={(e) => setFilters({ ...filters, sector: e.target.value })}
              className="w-56 rounded border border-gray-300 p-2 bg-white text-black"
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="institucion" className="text-sm font-medium mb-1 text-white">Institución:</label>
            <input
              type="text"
              id="institucion"
              value={filters.institucion}
              onChange={(e) => setFilters({ ...filters, institucion: e.target.value })}
              className="w-56 rounded border border-gray-300 p-2 bg-white text-black"
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="estado" className="text-sm font-medium mb-1 text-white">Estado:</label>
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
        </div>
        <div className="flex gap-2 mt-2">
          <button onClick={handleSearch} className="px-4 py-2 bg-blue-600 text-white rounded">Buscar</button>
          <button onClick={handleClearFilters} className="px-4 py-2 bg-gray-300 text-black rounded">Borrar Filtros</button>
        </div>
      </div>

      <div className="px-4 pb-6 md:px-6 xl:px-7.5">
        {error && <p className="text-red-500">Error: {error}</p>}
        {loading ? (
          <p className="text-white">Cargando...</p>
        ) : (
          <DynamicTable
            columns={columns}
            data={filteredUbicaciones}
            withFilters={false}
            onSearch={handleSearch}
            withActions={false}
          />
        )}
      </div>
    </div>
  );
};

export default ListarUbicaciones;
