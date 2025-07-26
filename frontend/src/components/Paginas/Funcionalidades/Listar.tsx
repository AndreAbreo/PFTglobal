"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface Perfil {
  id: number;
  nombrePerfil: string;
  estado: string;
}

interface Funcionalidad {
  id: number;
  nombreFuncionalidad: string;
  ruta: string;
  estado: string;
  perfiles: Perfil[];
}

const ListarFuncionalidades: React.FC = () => {
  const [funcionalidades, setFuncionalidades] = useState<Funcionalidad[]>([]);
  const [filteredFuncionalidades, setFilteredFuncionalidades] = useState<Funcionalidad[]>([]);
  const [perfilesUnicos, setPerfilesUnicos] = useState<string[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const [filters, setFilters] = useState({ nombreFuncionalidad: "", estado: "ACTIVO", perfiles: "" });

  const fetchFuncionalidades = async () => {
    setLoading(true);
    try {
      const data = await fetcher<Funcionalidad[]>("/funcionalidades/listar", { method: "GET" });
      setFuncionalidades(data);
      setFilteredFuncionalidades(data);

      const perfilesSet = new Set<string>();
      data.forEach(item => item.perfiles.forEach(p => perfilesSet.add(p.nombrePerfil)));
      setPerfilesUnicos(Array.from(perfilesSet));

    } catch (err: any) {
      setError(err.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchFuncionalidades();
  }, []);

  const applyFilters = (data: Funcionalidad[], filters: { nombreFuncionalidad: string; estado: string; perfiles: string }) => {
    return data.filter((item) => {
      const matchNombre = item.nombreFuncionalidad.toLowerCase().includes(filters.nombreFuncionalidad.toLowerCase());
      const matchEstado = filters.estado ? item.estado === filters.estado : true;
      const matchPerfiles = filters.perfiles ? item.perfiles.some(p => p.nombrePerfil === filters.perfiles) : true;
      return matchNombre && matchEstado && matchPerfiles;
    });
  };

  const handleSearch = () => {
    setFilteredFuncionalidades(applyFilters(funcionalidades, filters));
  };

  const handleClearFilters = () => {
    const clearedFilters = { nombreFuncionalidad: "", estado: "", perfiles: "" };
    setFilters(clearedFilters);
    setFilteredFuncionalidades(applyFilters(funcionalidades, clearedFilters));
  };

  const columns: Column<Funcionalidad>[] = [
    { header: "Nombre", accessor: "nombreFuncionalidad", type: "text", filterable: true },
    { header: "Ruta", accessor: "ruta", type: "text", filterable: false },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
    {
      header: "Perfiles",
      accessor: (row) => row.perfiles.map(p => p.nombrePerfil).join(", "),
      type: "text",
      filterable: false
    }
  ];

  return (
    <>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <>
          <div className="mb-4">
            <h2 className="text-xl font-bold mb-2">Filtros</h2>
            <div className="flex flex-wrap gap-4 items-end">
              <div className="flex flex-col">
                <label htmlFor="nombreFuncionalidad" className="text-sm font-medium mb-1">Nombre:</label>
                <input
                  type="text"
                  id="nombreFuncionalidad"
                  value={filters.nombreFuncionalidad}
                  onChange={(e) => setFilters({ ...filters, nombreFuncionalidad: e.target.value })}
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
              <div className="flex flex-col">
                <label htmlFor="perfilFilter" className="text-sm font-medium mb-1">Perfiles:</label>
                <select
                  id="perfilFilter"
                  value={filters.perfiles}
                  onChange={(e) => setFilters({ ...filters, perfiles: e.target.value })}
                  className="w-52 rounded border border-gray-300 p-2 bg-white text-black"
                >
                  <option value="">Todos</option>
                  {perfilesUnicos.map((perfil) => (
                    <option key={perfil} value={perfil}>{perfil}</option>
                  ))}
                </select>
              </div>
              <div className="flex gap-2 mt-1">
                <button onClick={handleSearch} className="px-4 py-2 bg-blue-600 text-white rounded">Buscar</button>
                <button onClick={handleClearFilters} className="px-4 py-2 bg-gray-300 text-black rounded">Borrar Filtros</button>
              </div>
            </div>
          </div>
          <DynamicTable
            columns={columns}
            data={filteredFuncionalidades}
            withFilters={false}
            onSearch={handleSearch}
            withActions={true}
            deleteUrl="/funcionalidades/eliminar"
            basePath="/funcionalidades"
            sendOnlyId={false}
            initialFilters={filters}
          />
        </>
      )}
    </>
  );
};

export default ListarFuncionalidades;
