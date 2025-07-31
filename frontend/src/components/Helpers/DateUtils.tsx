


export const toISODateTimeString = (fecha: Date): string => {
  return fecha.getFullYear() + '-' + 
    String(fecha.getMonth() + 1).padStart(2, '0') + '-' + 
    String(fecha.getDate()).padStart(2, '0') + 'T' + 
    String(fecha.getHours()).padStart(2, '0') + ':' + 
    String(fecha.getMinutes()).padStart(2, '0') + ':' + 
    String(fecha.getSeconds()).padStart(2, '0');
};


export const formatDateTimeForBackend = (fechaHora: string): string => {
  const fecha = new Date(fechaHora);
  return toISODateTimeString(fecha);
};


export const formatDateForDisplay = (fecha: Date): string => {
  return fecha.toLocaleString('es-UY', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};


export const isoToDateTimeLocal = (fechaISO: string): string => {
  try {
    const fecha = new Date(fechaISO);
    if (isNaN(fecha.getTime())) {
      console.warn('Fecha inválida:', fechaISO);
      return '';
    }
    return new Date(fecha.getTime() - fecha.getTimezoneOffset() * 60000)
      .toISOString().slice(0, 16);
  } catch (error) {
    console.error('Error al convertir fecha ISO:', error);
    return '';
  }
}; 