const config: Record<'dev' | 'prod', { apiBaseUrl: string }> = {
  dev: {
    apiBaseUrl: 'http://localhost:8080',
  },
  prod: {
    apiBaseUrl: '/api',
  },
};

export default config[import.meta.env.MODE as 'dev' | 'prod' || 'dev'];
