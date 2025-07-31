


export function validation_digit(ci: string): number {
  let a = 0;
  let i = 0;

  if (ci.length <= 6) {
    for (i = ci.length; i < 7; i++) {
      ci = '0' + ci;
    }
  }

  for (i = 0; i < 7; i++) {
    a += (parseInt("2987634"[i]) * parseInt(ci[i])) % 10;
  }
  
  if (a % 10 === 0) {
    return 0;
  } else {
    return 10 - a % 10;
  }
}


export function validate_ci(ci: string): boolean {
  ci = clean_ci(ci);

  if (ci.length < 7) {
    return false;
  }
  
  const dig = ci[ci.length - 1];
  ci = ci.replace(/[0-9]$/, '');
  
  return dig === validation_digit(ci).toString();
}


export function random_ci(): string {
  const ci = Math.floor(Math.random() * 10000000).toString();
  const fullCi = ci.substring(0, 7) + validation_digit(ci);
  return fullCi;
}


export function clean_ci(ci: string): string {
  return ci.replace(/\D/g, '');
}


export function format_ci(ci: string): string {
  ci = clean_ci(ci);
  
  if (ci.length < 7) {
    return ci;
  }
  
  const base = ci.substring(0, ci.length - 1);
  const digit = ci[ci.length - 1];

  const formatted = base.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
  
  return `${formatted}-${digit}`;
}


export function validateAndFormatCI(ci: string): {
  isValid: boolean;
  formatted: string;
  clean: string;
  error?: string;
} {
  const clean = clean_ci(ci);
  
  if (clean.length === 0) {
    return {
      isValid: false,
      formatted: ci,
      clean: clean,
      error: "La cédula no puede estar vacía"
    };
  }
  
  if (clean.length < 7) {
    return {
      isValid: false,
      formatted: ci,
      clean: clean,
      error: "La cédula debe tener al menos 7 dígitos"
    };
  }
  
  if (clean.length > 8) {
    return {
      isValid: false,
      formatted: ci,
      clean: clean,
      error: "La cédula no puede tener más de 8 dígitos"
    };
  }
  
  const isValid = validate_ci(clean);
  
  return {
    isValid,
    formatted: format_ci(clean),
    clean: clean,
    error: isValid ? undefined : "Cédula inválida"
  };
} 