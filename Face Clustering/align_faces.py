"""Performs face alignment and stores face thumbnails in the output directory."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from scipy import misc
import tensorflow as tf
import numpy as np
import facenet
import align.detect_face

def get_aligned_faces(image):
    MARGIN=44
    IMAGE_SIZE=160
    with tf.Graph().as_default():
        gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=1)
        sess = tf.Session(config=tf.ConfigProto(gpu_options=gpu_options, log_device_placement=False))
        with sess.as_default():
            pnet, rnet, onet = align.detect_face.create_mtcnn(sess, None)

    minsize = 20  # minimum size of face
    threshold = [0.6, 0.7, 0.7]  # three steps's threshold
    factor = 0.709  # scale factor
    try:
        img = misc.imread(image)
    except (IOError, ValueError, IndexError) as e:
        errorMessage = '{}: {}'.format(image, e)
        print(errorMessage)
    else:
        if img.ndim < 2:
            print('Unable to align "%s"' % image)
            return None
        if img.ndim == 2:
            img = facenet.to_rgb(img)
        img = img[:, :, 0:3]

        bounding_boxes, _ = align.detect_face.detect_face(img, minsize, pnet, rnet, onet, threshold,
                                                          factor)
        nrof_faces = bounding_boxes.shape[0]
        if nrof_faces > 0:
            det = bounding_boxes[:, 0:4]
            det_arr = []
            img_size = np.asarray(img.shape)[0:2]
            if nrof_faces > 1:

                bounding_box_size = (det[:, 2] - det[:, 0]) * (det[:, 3] - det[:, 1])
                img_center = img_size / 2
                offsets = np.vstack([(det[:, 0] + det[:, 2]) / 2 - img_center[1],
                                     (det[:, 1] + det[:, 3]) / 2 - img_center[0]])
                offset_dist_squared = np.sum(np.power(offsets, 2.0), 0)
                index = np.argmax(
                    bounding_box_size - offset_dist_squared * 2.0)  # some extra weight on the centering
                det_arr.append(det[index, :])
            else:
                det_arr.append(np.squeeze(det))
            det = np.squeeze(det_arr[0])
            bb = np.zeros(4, dtype=np.int32)
            bb[0] = np.maximum(det[0] - MARGIN / 2, 0)
            bb[1] = np.maximum(det[1] - MARGIN / 2, 0)
            bb[2] = np.minimum(det[2] + MARGIN / 2, img_size[1])
            bb[3] = np.minimum(det[3] + MARGIN / 2, img_size[0])
            cropped = img[bb[1]:bb[3], bb[0]:bb[2], :]
            scaled = misc.imresize(cropped, (IMAGE_SIZE, IMAGE_SIZE), interp='bilinear')
            # filename_base, file_extension = os.path.splitext(image)
            # output_filename_n = "{}{}".format(filename_base, file_extension)
            # misc.imsave(output_filename_n, scaled)
            return scaled
        else:
            return "image not found"

if __name__ == '__main__':
    # get_aligned_faces(parse_arguments(sys.argv[1:]))
    get_aligned_faces("/home/lenovo/Desktop/IMG_20180925_163603.jpg")
