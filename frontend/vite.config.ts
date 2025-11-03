import {defineConfig} from 'vite';
import react from '@vitejs/plugin-react';
import viteTsconfigPaths from 'vite-tsconfig-paths';
import {visualizer} from "rollup-plugin-visualizer";
import viteCompression from 'vite-plugin-compression';

export default defineConfig({
	base: '/',
	build: {
		outDir: 'build',
	},
	plugins: [
		viteCompression({
			algorithm: 'gzip',
			ext: '.gz',
			threshold: 1024,
			deleteOriginFile: false
		}),
		react(),
		viteTsconfigPaths(),
		visualizer({
			filename: './dist/stats.html',
			open: true
		})
	],
	server: {
		open: true,
		port: 3000,
	},
});